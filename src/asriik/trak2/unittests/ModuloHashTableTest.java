package asriik.trak2.unittests;

import asriik.trak2.HashTable;
import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by: harakka
 * Date and time: 31.3.2013, 19:22
 */
public class ModuloHashTableTest {
    HashTable hashtable;

    @BeforeMethod
    public void setUp() throws Exception {
        hashtable = new HashTable(13, 0.75, HashTable.HashType.MODULO);
        for (int i: new int[]{-1,0,2, 100, 9999, 666}) {
            hashtable.add(i);
        }
    }

    @Test
    public void testContains() throws Exception {
        Assert.assertTrue(hashtable.contains(9999));
        Assert.assertFalse(hashtable.contains(-2));
    }

    @Test
    public void testAdd() throws Exception {
        hashtable.add(-3);
        Assert.assertTrue(hashtable.contains(-3));
    }

    @Test
    public void testRemove() throws Exception {
        hashtable.remove(9999);
        Assert.assertFalse(hashtable.contains(9999));
    }

    @Test
    public void testClean() throws Exception {
        hashtable.clean();
        Assert.assertEquals(hashtable.getSize(), 0);
    }
}
