package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlatAvitoTest {

    FlatAvito flatAvito ;

    @BeforeEach
    void prepareData(){
        flatAvito = new FlatAvito("1  500 400 ₽ 37 433 ₽ за м²","t","city","address","href");
    }
    @Test
    void testCalculatePricePerMeter() {
        assertEquals(flatAvito.getPricePerMeter(),37433);
    }

    @Test
    void cutToPattern() {
    }
}