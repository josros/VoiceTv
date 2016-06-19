package de.jro.androspacelib;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jro on 25/04/16.
 */
public class TvControlImpl implements ITvControl {

    private final String deviceIp;

    public TvControlImpl(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    @Override
    public void enterKey(final Key key) {
        final String url = String.format("http://%s:1925/1/input/key", deviceIp);
        KeyDto keyDto = new KeyDto();
        keyDto.setKey(key.getJointspaceValue());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation(url, keyDto);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }
}
