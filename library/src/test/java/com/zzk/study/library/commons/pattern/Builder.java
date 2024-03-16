package com.zzk.study.library.commons.pattern;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Builder {

    @Test
    public void whenCalledtoHashCode_thenCorrect() {
        int hashcode = new HashCodeBuilder(17, 37)
                .append("John")
                .append("john@domain.com")
                .toHashCode();
        assertThat(hashcode).isEqualTo(1269178828);
    }
}
