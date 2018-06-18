package gmail.garyy7811.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: GaryY
 * Date: 6/17/2018
 */
public class XXXCCRCache extends LinkedHashMap<Integer, String>{

    private int    cacheSizeLimit;
    private int    cacheTimeLimitInSec;
    private String dateFormatPattern;

    public XXXCCRCache( int cacheSizeLimit, int cacheTimeLimitInSec, String dateFormatPattern ){
        this.cacheSizeLimit = cacheSizeLimit;
        this.cacheTimeLimitInSec = cacheTimeLimitInSec;
        this.dateFormatPattern = dateFormatPattern;
    }

    @Autowired
    ObjectMapper objectMapper;

    private ThreadLocal<SimpleDateFormat> simpleDateFormatter = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue(){
            return new SimpleDateFormat( dateFormatPattern );
        }
    };

    @Override
    protected boolean removeEldestEntry( Map.Entry<Integer, String> eldest ){
        final Set<Map.Entry<Integer, String>> entries = entrySet();
        final Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();

        Map.Entry<Integer, String> first = iterator.next();

        Map firstMap;
        try{
            firstMap = objectMapper.readValue( first.getValue(), Map.class );
        }
        catch( IOException e ){
            throw new RuntimeException( e );
        }
        long firstTime;
        try{
            Map itemMap = ( Map )firstMap.get( "item" );
            firstTime = simpleDateFormatter.get().parse( ( String )itemMap.get( "timestamp" ) ).getTime();
        }
        catch( ParseException e ){
            throw new RuntimeException( e );
        }
        if( entries.size() > cacheSizeLimit ){
            return firstTime + cacheTimeLimitInSec * 1000 < System.currentTimeMillis();
        }
        return false;
    }
}
