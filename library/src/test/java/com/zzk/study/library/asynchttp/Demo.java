package com.zzk.study.library.asynchttp;

import org.asynchttpclient.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/4/20 8:41 PM
 */
public class Demo {

    public static void downloadWithAHC(String url, String localFilename) throws ExecutionException, InterruptedException, IOException {

        FileOutputStream stream = new FileOutputStream(localFilename);
        AsyncHttpClient client = Dsl.asyncHttpClient();

        client.prepareGet(url)
                .execute(new AsyncCompletionHandler<FileOutputStream>() {

                    @Override
                    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                        stream.getChannel()
                                .write(bodyPart.getBodyByteBuffer());
                        return State.CONTINUE;
                    }

                    @Override
                    public FileOutputStream onCompleted(Response response) throws Exception {
                        return stream;
                    }
                })
                .get();

        stream.getChannel().close();
        client.close();
    }
}
