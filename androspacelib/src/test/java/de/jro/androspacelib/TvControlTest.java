package de.jro.androspacelib;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.client.RestTemplate;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TvControlImpl.class)
public class TvControlTest {

    @Captor
    private ArgumentCaptor<String> urlArgument;

    @Captor
    private ArgumentCaptor<KeyDto> keyArgument;

    private TvControlImpl tvControl = new TvControlImpl("foohost");

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEnterKey() throws Exception {
        RestTemplate restTemplateMock = PowerMockito.mock(RestTemplate.class);
        PowerMockito.whenNew(RestTemplate.class)
                .withNoArguments().thenReturn(restTemplateMock).getMock();
        // call method with DIGIT1
        tvControl.enterKey(Key.DIGIT1);
        // verify POST
        Mockito.verify(restTemplateMock).postForLocation(urlArgument.capture(), keyArgument.capture());
        String capturedUrl = urlArgument.getValue();
        // url is as expected
        Assert.assertEquals("http://foohost:1925/1/input/key", capturedUrl);
        KeyDto capturedKey = keyArgument.getValue();
        ObjectMapper mapper = new ObjectMapper();
        String keyAsJson = mapper.writeValueAsString(capturedKey);
        // json is as expected
        Assert.assertEquals("{\"key\":\"Digit1\"}", keyAsJson);
    }
}