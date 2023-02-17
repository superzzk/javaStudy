package zzk.study.java.core.lang;


import org.junit.Test;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/5/7 11:38 PM
 */
public class PackageDemo {
    private static final String pack = "java.lang";
    @Test
    public void demo() {
        PackageDemo classMethods = new PackageDemo();
        classMethods.toStringMethod();
        classMethods.isSealedMethod();
        classMethods.isCompatibleWithMethod();
        classMethods.getSpecificationVersionMethod();
        classMethods.getSpecificationVendorMethod();
        classMethods.getSpecificationTitleMethod();
        classMethods.getPackagesMethod();
        classMethods.getPackageMethod();
        classMethods.getNameMethod();
        classMethods.getImplementationVersionMethod();
        classMethods.getImplementationVendorMethod();
        classMethods.getImplementationTitleMethod();
    }

    @Test
    public void toStringMethod() {
        // get the java lang package
        Package pack = Package.getPackage(PackageDemo.pack);

        // print the package as a string
        System.out.println("" + pack.toString());
    }

    public void isSealedMethod() {
        // get the java lang package
        Package pack = Package.getPackage("java.lang");

        // check if this package is sealed
        System.out.println("" + pack.isSealed());
    }

    public void isCompatibleWithMethod() {
        // get the java lang package
        Package pack = Package.getPackage("java.lang");

        // check if this package is compatible with version 1.4.6
        System.out.println("" + pack.isCompatibleWith("1.8"));
    }

    public void getSpecificationVersionMethod() {
        // get the java lang package
        Package pack = Package.getPackage("java.lang");

        // print the specification version for this package
        System.out.println("" + pack.getSpecificationVersion());
    }

    public void getSpecificationVendorMethod() {
        // get the java lang package
        Package pack = Package.getPackage("java.lang");

        // print the specification vendor for this package
        System.out.println("" + pack.getSpecificationVendor());
    }

    public void getSpecificationTitleMethod() {
        // get the java lang package
        Package pack = Package.getPackage("java.lang");

        // print the specification title for this package
        System.out.println("" + pack.getSpecificationTitle());
    }

    public void getPackagesMethod() {
        // get all the packages
        Package[] pack = Package.getPackages();

        // print all packages, one by one
        for (int i = 0; i < pack.length; i++) {
            System.out.println("" + pack[i]);
        }
    }

    public void getPackageMethod() {
        // create a package object for java.lang package
        Package pack = Package.getPackage("java.lang");

        // get the fully qualified name for this package
        System.out.println("" + pack.getName());
    }

    public void getNameMethod() {
        // create a package object for java.lang package
        Package pack = Package.getPackage("java.lang");

        // get the fully qualified name for this package
        System.out.println("" + pack.getName());
    }

    public void getImplementationVersionMethod() {
        // create a package object for java.lang package
        Package pack = Package.getPackage("java.lang");

        // get the implementation version
        System.out.println("" + pack.getImplementationVersion());
    }

    public void getImplementationVendorMethod() {
        // create a package object for java.lang package
        Package pack = Package.getPackage("java.lang");

        // get the implementation vendor
        System.out.println("" + pack.getImplementationVendor());
    }

    public void getImplementationTitleMethod() {
        // create a package object for java.lang package
        Package pack = Package.getPackage("java.lang");

        // get the annotation for lang package
        System.out.println("" + pack.getImplementationTitle());
    }
}

