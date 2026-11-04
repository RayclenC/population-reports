package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        /*
        App app = new App();
        if(args.length < 1){
            app.Connect("localhost:33060", 30000);
        }else{
            app.Connect(args[0], Integer.parseInt(args[1]));
        }

         */

    }

    // Integrated test methods go here

    /*

            ** EXAMPLE **

        @Test
        void testGetEmployee()
        {
            Employee emp = app.getEmployee(255530);
            assertEquals(emp.emp_no, 255530);
            assertEquals(emp.first_name, "Ronghao");
            assertEquals(emp.last_name, "Garigliano");
        }

    */
}