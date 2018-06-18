package gmail.garyy7811.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: GaryY
 * Date: 6/1/2018
 */
@RestController
public class JavaCode{

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GiphyApi giphyApi;

    @Value( "${giphyApiLimit}" )
    int giphyResltLimit;

    public String transformGiphyFormat( String gifJsn ) throws IOException{
        Map giphyRslt = objectMapper.readValue( gifJsn, Map.class );

        List<HashMap<String, String>> gphDataLst = ( List<HashMap<String, String>> )giphyRslt.get( "data" );

        List<HashMap<String, String>> dataLst = null;
        if( gphDataLst.size() == giphyResltLimit ){
            dataLst = gphDataLst.stream().map( m -> {
                final HashMap<String, String> tmp = new HashMap();
                tmp.put( "gif_id", m.get( "id" ) );
                tmp.put( "url", m.get( "url" ) );
                return tmp;
            } ).collect( Collectors.toList() );
        }
        else{
            dataLst = new ArrayList<>();
        }


        final HashMap<String, List<HashMap<String, String>>> rtMap = new HashMap<>();
        rtMap.put( "data", dataLst );
        final String rtS = objectMapper.writeValueAsString( rtMap );
        return rtS;
    }

    @Autowired
    XXXCCRCache xxxccrCache;

    @RequestMapping( value = "/items", method = RequestMethod.POST )
    public ResponseEntity addItem( @RequestBody String reqStr ) throws IOException{
        Map reqMap = objectMapper.readValue( reqStr, Map.class );
        Map itemMap = ( Map )reqMap.get( "item" );
        xxxccrCache.put( ( Integer )itemMap.get( "id" ), reqStr );
        return new ResponseEntity( HttpStatus.CREATED );
    }

    @RequestMapping( value = "/items", method = RequestMethod.GET )
    public @ResponseBody
    String getItems(){
        final String rt = "[" + String.join( ", ", xxxccrCache.values() ) + "]";
        return rt;
    }

}
