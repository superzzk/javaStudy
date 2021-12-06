package zzk.study.java.core.util.concurrent.atomic.aba;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountUnitTest {

    @Test
    public void depositTest() {
        Account account = new Account();
        final int moneyToDeposit = 50;

        assertTrue(account.deposit(moneyToDeposit));

        assertEquals(moneyToDeposit, account.getBalance());
    }

    @Test
    public void withdrawTest() throws InterruptedException {
        Account account = new Account();
        final int defaultBalance = 50;
        final int moneyToWithdraw = 20;

        account.deposit(defaultBalance);

        assertTrue(account.withdraw(moneyToWithdraw));

        assertEquals(defaultBalance - moneyToWithdraw, account.getBalance());
    }

    @Test
    public void abaProblemTest() throws InterruptedException {
        Account account = new Account();
        final int defaultBalance = 50;

        final int amountToWithdrawByThread1 = 20;
        final int amountToWithdrawByThread2 = 10;
        final int amountToDepositByThread2 = 10;

        assertEquals(0, account.getTransactionCount());
        assertEquals(0, account.getCurrentThreadCASFailureCount());
        //存入50
        account.deposit(defaultBalance);
        assertEquals(1, account.getTransactionCount());//确认发生1比交易

        /*
        * 开启2个线程，线程1会延时2s执行
        * */
        Thread thread1 = new Thread(() -> {

            // this will take longer due to the name of the thread
            assertTrue(account.withdraw(amountToWithdrawByThread1));//取出20

            // thread 1 fails to capture ABA problem
            // 实际上出现的变动，但是通过CAS的方式并没有感知
            assertNotEquals(1, account.getCurrentThreadCASFailureCount());
            assertEquals(0, account.getCurrentThreadCASFailureCount());

        }, "thread1");

        Thread thread2 = new Thread(() -> {

            assertTrue(account.deposit(amountToDepositByThread2));//存10
            assertEquals(defaultBalance + amountToDepositByThread2, account.getBalance());//当前账户应该是60

            // this will be fast due to the name of the thread
            assertTrue(account.withdraw(amountToWithdrawByThread2));//取出10

            // thread 1 didn't finish yet, so the original value will be in place for it
            assertEquals(defaultBalance, account.getBalance());//账户依然是50

            assertEquals(0, account.getCurrentThreadCASFailureCount());//无cas失败
        }, "thread2");

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // compareAndSet operation succeeds for thread 1
        assertEquals(defaultBalance - amountToWithdrawByThread1, account.getBalance());

        //but there are other transactions
        assertNotEquals(2, account.getTransactionCount());

        // thread 2 did two modifications as well
        assertEquals(4, account.getTransactionCount());
    }
}
