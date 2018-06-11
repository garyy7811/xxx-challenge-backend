package gmail.garyy7811.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = "/root-test.xml" )
public class Testxxx{

    @Autowired
    MessageChannel tstInput;
    @Autowired
    QueueChannel   tstOutput;
    @Autowired
    ObjectMapper   objectMapper;


    @Test
    public void integrationTst() throws IOException{
        tstInput.send( MessageBuilder.createMessage( "", new MessageHeaders( Collections.singletonMap( "searchTerm", "love" ) ) ) );


        Assert.assertEquals( 1, tstOutput.getQueueSize() );
        Message<?> rtMsg = tstOutput.receive();
        Assert.assertEquals( 3, rtMsg.getHeaders().size() );
        Assert.assertEquals( "love", rtMsg.getHeaders().get( "searchTerm" ) );

        Map mp = objectMapper.readValue( rtMsg.getPayload().toString(), Map.class );

        List<Map> lst = ( List )mp.get( "data" );
        Assert.assertEquals( 5, lst.size() );

        lst.forEach( elmp -> {
            Assert.assertEquals( 2, elmp.size() );
            Assert.assertTrue( elmp.containsKey( "gif_id" ) );
            Assert.assertTrue( elmp.containsKey( "url" ) );
        } );


    }

}