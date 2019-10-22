package java8.optional;

import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * 一个使用Optional的例子（主要做返回值使用）
  **/
public class Address {
    private final String addressLine;  // never null
    private final String city;         // never null
    private final String postcode;     // optional, thus may be null

    // constructor ensures non-null fields really are non-null
    // optional field can just be stored directly, as null means optional
    public Address(String addressLine, String city, String postcode) {
        this.addressLine = Preconditions.checkNotNull(addressLine);
        this.city = Preconditions.checkNotNull(city);
        this.postcode = postcode;
    }

    // normal getters
    public String getAddressLine() { return addressLine; }
    public String getCity() { return city; }

    // special getter for optional field
    public Optional<String> getPostcode() {
        return Optional.ofNullable(postcode);
    }

    // return optional instead of null for business logic methods that may not find a result
    public static Optional<Address> findAddress(String userInput) {
        // find the address, returning Optional.empty() if not found
        return Optional.empty();
    }
}