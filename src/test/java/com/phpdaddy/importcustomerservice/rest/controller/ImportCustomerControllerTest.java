package com.phpdaddy.importcustomerservice.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phpdaddy.importcustomerservice.dao.CacheRepository;
import com.phpdaddy.importcustomerservice.exception.ExceptionControllerAdvice;
import com.phpdaddy.importcustomerservice.helper.CacheHelper;
import com.phpdaddy.importcustomerservice.helper.CustomerHelper;
import com.phpdaddy.importcustomerservice.model.Cache;
import com.phpdaddy.importcustomerservice.model.customer.Contact;
import com.phpdaddy.importcustomerservice.model.customer.Customer;
import com.phpdaddy.importcustomerservice.model.customer.Date;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParserSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.integration.file.remote.InputStreamCallback;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportCustomerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private Exchange exchange;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private FtpRemoteFileTemplate ftpTemplate;

    @Spy
    private CsvParserSettings csvParserSettings;

    @Mock
    private BeanListProcessor<Customer> customerBeanListProcessor;

    @Mock
    private CacheRepository cacheRepository;

    @InjectMocks
    private ImportCustomerController customerController;

    @Spy
    private ObjectMapper objectMapper;

    @Spy
    @InjectMocks
    private CacheHelper cacheHelper;

    @Autowired
    private CacheHelper h;

    @Spy
    private CustomerHelper customerHelper;

    private ArrayList<Customer> samples;
    private ArrayList<Customer> samplesWithExtraFields;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(customerController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Test
    public void testImportCustomers_EmptyCache() throws Exception {
        given(customerBeanListProcessor.getBeans()).willReturn(samples);
        given(cacheRepository.findByPid(samplesWithExtraFields.get(0).getPid())).willReturn(null);
        given(cacheRepository.findByPid(samplesWithExtraFields.get(1).getPid())).willReturn(null);

        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.queued", hasSize(2)))
                .andReturn();

        verify(cacheRepository, times(2)).findByPid(Matchers.anyInt());
        verify(cacheRepository, times(2)).save(Matchers.any(Cache.class));
        verify(rabbitTemplate, times(2)).convertAndSend(Matchers.anyString(), Matchers.eq("importCustomer.imported"), Matchers.anyString());
        verify(ftpTemplate, times(1)).get(Matchers.anyString(), Matchers.any(InputStreamCallback.class));
        verify(customerBeanListProcessor, times(1)).getBeans();
        verifyNoMoreInteractions(rabbitTemplate, ftpTemplate, customerBeanListProcessor, cacheRepository);
    }


    @Test
    public void testImportCustomers_CacheExists_NoUpdateNeeded() throws Exception {
        given(customerBeanListProcessor.getBeans()).willReturn(samples);
        given(cacheRepository.findByPid(samplesWithExtraFields.get(0).getPid())).willReturn(h.create(samplesWithExtraFields.get(0)));
        given(cacheRepository.findByPid(samplesWithExtraFields.get(1).getPid())).willReturn(h.create(samplesWithExtraFields.get(1)));

        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skipped", hasSize(2)))
                .andReturn();

        verify(cacheRepository, times(2)).findByPid(Matchers.anyInt());
        verify(ftpTemplate, times(1)).get(Matchers.anyString(), Matchers.any());
        verify(customerBeanListProcessor, times(1)).getBeans();
        verifyNoMoreInteractions(rabbitTemplate, ftpTemplate, customerBeanListProcessor, cacheRepository);
    }

    @Before
    public void initSamples() {
        Customer u = new Customer();
        u.setId("irrelevantId1");
        u.setPid(123);
        u.setFirstName("irrelevantName1");
        u.setLastName("irrelevantSurname1");
        u.setUsername("irrelevantSurname1");
        Contact contact = new Contact();
        contact.setEmail("user1@mail.cz");
        contact.setMobile("+32231232313");
        contact.setPhone("123123");
        u.setContact(contact);

        samples = new ArrayList<>();
        samples.add(u);

        u = new Customer();
        u.setId("irrelevantId2");
        u.setPid(456);
        u.setFirstName("irrelevantName2");
        u.setLastName("irrelevantSurname2");
        contact = new Contact();
        contact.setEmail("user2@mail.cz");
        contact.setMobile("+32231232313");
        contact.setPhone("123123");
        u.setContact(contact);

        samples.add(u);

        this.setSamplesWithExtraFields();
    }

    private void setSamplesWithExtraFields() {
        samplesWithExtraFields = new ArrayList<>();
        for (Customer customer : samples) {
            customer.setUsername(customer.getContact().getEmail().substring(0,
                    customer.getContact().getEmail().indexOf("@")));

            customer.setPassword(customer.getUsername());
            Date date = new Date();
            date.setCreatedDate(java.util.Date.from(Instant.now()));
            date.setUpdatedDate(java.util.Date.from(Instant.now()));
            customer.setDate(date);
            samplesWithExtraFields.add(customer);
        }
    }
}
