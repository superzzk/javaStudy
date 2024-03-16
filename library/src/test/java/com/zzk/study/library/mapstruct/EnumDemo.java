package com.zzk.study.library.mapstruct;

import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnumDemo {

    LevelMapper levelMapper = Mappers.getMapper(LevelMapper.class);

    @Test
    void givenHighInputLevel_WhenInputLevelToOutputLevel_ThenHighOutputLevel() {
        assertEquals(OutputLevel.HIGH, levelMapper.inputLevelToOutputLevel(InputLevel.HIGH));
    }

    @Test
    void givenMediumInputLevel_WhenInputLevelToOutputLevel_ThenThrows() {
        assertThrows(IllegalArgumentException.class, () -> levelMapper.inputLevelToOutputLevel(InputLevel.MEDIUM));
    }

    @Test
    void givenLowInputLevel_WhenInputLevelToOutputLevel_ThenLowOutputLevel() {
        assertEquals(OutputLevel.LOW, levelMapper.inputLevelToOutputLevel(InputLevel.LOW));
    }


    @Mapper
    interface LevelMapper {
        @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.THROW_EXCEPTION)
        OutputLevel inputLevelToOutputLevel(InputLevel inputLevel);
    }

    enum OutputLevel {
        LOW, HIGH
    }

    enum InputLevel {
        LOW, MEDIUM, HIGH
    }
}
