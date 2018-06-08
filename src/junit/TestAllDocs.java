package junit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import plagiarismdetector.IPlagiarismDetector;
import plagiarismdetector.impl.PlagiarismDetector;
//import plagiarismdetector.sol.PlagiarismDetector;

public class TestAllDocs
{
    private static final String ALLDOCS = "docs/alldocs";
    
    static IPlagiarismDetector detector;
    
    @BeforeClass
    public static void makeDetector() throws Exception {
        detector = new PlagiarismDetector(4);
        detector.readFilesInDirectory(new File(ALLDOCS));
    }

    @Test
    public void testNumDocuments() {
        assertEquals(929, detector.getFilenames().size());
    }
    
    @Test
    public void testSuspiciousDocuments() {
        Collection<String> pairs = detector.getSuspiciousPairs(200);
        assertEquals(6, pairs.size());
        assertTrue(pairs.contains("doc601402144.txt doc637510813.txt 311"));
        assertTrue(pairs.contains("doc308696253.txt doc482694494.txt 211"));
        assertTrue(pairs.contains("doc292933129.txt doc766799083.txt 414"));
        assertTrue(pairs.contains("doc581405996.txt doc767133926.txt 499"));
        assertTrue(pairs.contains("doc214896034.txt doc898886392.txt 238"));
        assertTrue(pairs.contains("doc160419942.txt doc634800066.txt 595"));
    }

}
