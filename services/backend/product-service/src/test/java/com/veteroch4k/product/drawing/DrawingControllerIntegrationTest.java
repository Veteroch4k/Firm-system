package com.veteroch4k.product.drawing;

import com.veteroch4k.product.BaseIntegrationTest;
import com.veteroch4k.product.repositories.DrawingRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class DrawingControllerIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    private DrawingRepository drawingRepository;
    
    @AfterEach
    void tearDown() {
        drawingRepository.deleteAll();
        
    }
    
    

}
