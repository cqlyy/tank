package cn.cq.test;


import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImagesTest {
    @Test
    void test(){
        try {
            BufferedImage read = ImageIO.read(ImagesTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            assertNotNull(read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
