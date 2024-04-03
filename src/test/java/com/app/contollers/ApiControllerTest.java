package com.app.contollers;

import com.app.utils.TextUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TextUtil textUtil;

    @Test
    public void testCalculateWithDates() throws Exception {
        String averageMonthlyEarnings = "50000";
        String startDate = "2024-06-01";
        String endDate = "2024-06-14";

        Mockito.when(textUtil.isDate(startDate, endDate)).thenReturn(true);
        Mockito.when(textUtil.isNumeric(averageMonthlyEarnings)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("average_monthly_earnings", averageMonthlyEarnings)
                        .param("start_date", startDate)
                        .param("end_date", endDate).param("with_tax", "false"))
                .andExpect(status().isOk())
                .andExpect(content().string("22184.301"));

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("average_monthly_earnings", averageMonthlyEarnings)
                        .param("start_date", startDate)
                        .param("end_date", endDate)
                        .param("with_tax", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("19300.342"));
    }

    @Test
    public void testCalculateWithVacationDays() throws Exception {
        String averageMonthlyEarnings = "100000";
        String vacationDays = "28";

        Mockito.when(textUtil.isNumeric(vacationDays, averageMonthlyEarnings)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("average_monthly_earnings", averageMonthlyEarnings)
                        .param("vacation_days", vacationDays))
                .andExpect(status().isOk())
                .andExpect(content().string("95563.140"));

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("average_monthly_earnings", averageMonthlyEarnings)
                        .param("vacation_days", vacationDays)
                        .param("with_tax", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("83139.932"));
    }

    @Test
    public void testInvalidParameters() throws Exception {
        String averageMonthlyEarnings = "not_numeric";
        String startDate = "2024-01-01";

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("average_monthly_earnings", averageMonthlyEarnings)
                        .param("start_date", startDate))
                .andExpect(status().isBadRequest());
    }
}