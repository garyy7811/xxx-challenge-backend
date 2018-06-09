package gmail.garyy7811.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User: GaryY
 * Date: 6/1/2018
 */
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
}
