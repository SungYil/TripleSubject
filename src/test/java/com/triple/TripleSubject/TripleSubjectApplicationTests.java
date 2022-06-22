package com.triple.TripleSubject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.EventRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TripleSubjectApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void whenPostReview_thenOk()throws Exception{

	}

	@Test
	void whenGetPointByUserId_thenOk()throws Exception{
		var result=requestGet("/points/3ede0ef2-92b7-4817-a5f3-0c575361f745");

		result.andDo(print()).andExpect(status().isOk());
	}
	@Test
	void whenGetPointByUserIdWithNotFoundId_thenError()throws Exception{
		var result=requestGet("/points/3ede0ef2-92b7-4817-a5f3-0c575361f123");

		result.andDo(print()).andExpect(status().isNotFound());
	}
	@Test
	void whenGetPointByUserIdWithTypeError_thenError()throws Exception{
		var result = requestGet("/points/1234");

		result.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void whenGetPoint_thenOk()throws Exception{
		var result = requestGet("/points");

		result.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void whenPostEventAdd_thenOk()throws Exception{
		/*var result = requestPost(new EventRequest(),"/events");

		result.andDo(print()).andExpect(status().isOk());*/
	}
	@Test
	void whenPostEventAddWithTypeError_thenError()throws Exception{
		/*var result=requestPost(new EventRequest(),"/events");

		result.andDo(print()).andExpect(status().isBadRequest());*/
	}

	@Test
	void whenPostEventMod_thenOk()throws Exception{
		/*var result=request(new EventRequest(),"/events");

		result.andDo(print()).andExpect(status().isOk());*/
	}
	@Test
	void whenPostEventModWithTypeError_thenError()throws Exception{
		/*var result=requestPost(new EventRequest(),"/events");

		result.andDo(print()).andExpect(status().isBadRequest());*/
	}
	@Test
	void whenPostEventModWithNotFoundError_thenError()throws Exception{
		/*var result = requestPost(new EventRequest(),"/events");

		result.andDo(print()).andExpect(status().isNotFound());*/
	}

	@Test
	void whenPostEventDelete_thenOk()throws Exception{
		/*var result = requestPost(new EventRequest(),"/events");

		result.andDo(print()).andExpect(status().isOk());*/
	}
	@Test
	void whenPostEventDeleteWithNotFoundError_thenError()throws Exception{
		/*var result = requestPost(new EventRequest(),"/events");

		result.andDo(print()).andExpect(status().isOk());*/
	}

	ResultActions requestPost(Object request,String path) throws Exception{
		return mockMvc.perform(
				MockMvcRequestBuilders.post(path)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(request))
		);
	}

	ResultActions requestGet(MultiValueMap<String,String> params, String path)throws Exception{
		return mockMvc.perform(
				MockMvcRequestBuilders.get(path).params(params)
		);
	}
	ResultActions requestGet(String path)throws Exception{
		return requestGet(new LinkedMultiValueMap<>(),path);
	}

}
