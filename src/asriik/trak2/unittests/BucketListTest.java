package asriik.trak2.unittests;

import asriik.trak2.BucketList;
import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * TestNG test for verifying some basic BucketList functionality.
 * Created by: Antti Riikonen
 * Date and time: 30.3.2013, 21:06
 */
public class BucketListTest {
    BucketList bucketlist;

    @BeforeMethod
    public void setUp() throws Exception {
        bucketlist = new BucketList();
    }

    @Test
    public void testContains() throws Exception {
        bucketlist.add(-5);
        bucketlist.add(0);
        bucketlist.add(1);
        bucketlist.add(9999);
        Assert.assertEquals(bucketlist.contains(2), false);
        Assert.assertEquals(bucketlist.contains(9999), true);
    }

    @Test
    public void testRemove() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {
        bucketlist.add(10);
        Assert.assertTrue(bucketlist.contains(10));
        Assert.assertFalse(bucketlist.contains(1));
    }

    @Test
    public void testValues() throws Exception {
        bucketlist.add(1);
        bucketlist.add(2);
        bucketlist.add(3);
        bucketlist.add(9999);
        Assert.assertTrue(Arrays.equals(bucketlist.values(), new int[]{9999,3,2,1}));
    }

    @Test
    public void testToString() throws Exception {
        bucketlist.add(1);
        bucketlist.add(2);
        bucketlist.add(3);
        bucketlist.add(9999);
        Assert.assertEquals("9999 → 3 → 2 → 1 → ", bucketlist.toString());
    }
}
