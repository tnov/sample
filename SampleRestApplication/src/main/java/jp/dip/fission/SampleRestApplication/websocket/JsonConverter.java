package jp.dip.fission.SampleRestApplication.websocket;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.stream.Stream;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.ws.rs.PathParam;

import org.apache.poi.ss.formula.functions.T;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter implements Decoder.Text<T>,Encoder.Text<T> {

	public static final String JSON_ENDPOINT_CLASS = "json.endpoint.class";

	private Map<String, Object> userProperties;
	private Class<?> decoderClass;

	@Override
	public void init(EndpointConfig config) {
		userProperties = config.getUserProperties();
	}

	private Class<?> getDecoderClass() {
		if (decoderClass == null) {
			Class<?> decorderEndpointClass = (Class<?>) userProperties.get(JSON_ENDPOINT_CLASS);
			Method method = Stream.of(decorderEndpointClass.getMethods())
				.filter(m -> m.isAnnotationPresent(OnMessage.class))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("method annotated by @OnMessage is not found."));
			Parameter parameter = Stream.of(method.getParameters())
				.filter(p -> !Session.class.isAssignableFrom(p.getType()) && !p.isAnnotationPresent(PathParam.class))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("message parameter is not found."));
			decoderClass = parameter.getType();
		}
		return  decoderClass;
	}

	@Override
	public void destroy() {
	}

	@Override
	public T decode(String s) throws DecodeException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (T) mapper.readValue(s, getDecoderClass());
		} catch (JsonParseException e) {
//			e.printStackTrace();
			throw new DecodeException(s, "failed to decode message.", e);
		} catch (JsonMappingException e) {
//			e.printStackTrace();
			throw new DecodeException(s, "failed to decode message.", e);
		} catch (IOException e) {
//			e.printStackTrace();
			throw new DecodeException(s, "failed to decode message.", e);
		}
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

	@Override
	public String encode(T object) throws EncodeException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
//			e.printStackTrace();
			throw new EncodeException(object, "failed to encode message.", e);
		}
	}
}
