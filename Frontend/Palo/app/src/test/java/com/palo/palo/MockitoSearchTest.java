package com.palo.palo;

import com.palo.palo.fragments.SearchFragment;
import com.spotify.sdk.android.authentication.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSearchTest {

    @Mock
    private SearchFragment f;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getResponseTest() {
        f = mock(SearchFragment.class);

        String searchCorrect = "url + \"/search?q=\" + dababy";

        when(f.makeStringSongRequest(searchCorrect)).thenReturn("{\"albums\":[{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0220e08c8cc23f404d723b5647\",\"name\":\"BLAME IT ON BABY\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/623PL2MBg50Br5dLXC9E9e\",\"id\":\"623PL2MBg50Br5dLXC9E9e\"},{\"artist\":\"Dua Lipa\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0249caa4fc6f962057ba65576a\",\"name\":\"Levitating (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/04m06KhJUuwe1Q487puIud\",\"id\":\"04m06KhJUuwe1Q487puIud\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02f2b94b2fda4f08836d6371ba\",\"name\":\"KIRK\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/1NsTSXjVNE7XmZ8PmyW0wl\",\"id\":\"1NsTSXjVNE7XmZ8PmyW0wl\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02e818d05b79be19f4d49f1ebf\",\"name\":\"BLAME IT ON BABY (DELUXE)\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/6SL49HqoUwu2hhfukBAK3Q\",\"id\":\"6SL49HqoUwu2hhfukBAK3Q\"},{\"artist\":\"SpotemGottem\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02637d8f4420da84bb0b50e757\",\"name\":\"Beat Box 3 (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/6QqwWNpKYbROin3Gq5pIrV\",\"id\":\"6QqwWNpKYbROin3Gq5pIrV\"}], \"artists\":[{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/9fabb3d7dd075894af0b3805c129e433b9104b26\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/4r63FhuTkUYltbVAg5TQnk\",\"id\":\"4r63FhuTkUYltbVAg5TQnk\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02f51d22db94b7ccbed26870c0\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/7MCrEuHBgUcjP8eMxM2IFC\",\"id\":\"7MCrEuHBgUcjP8eMxM2IFC\"},{\"artist\":\"DaBabyDan\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e028456a317b92641d713968e55\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/0SanfTMHZHJ7zds7GPBffd\",\"id\":\"0SanfTMHZHJ7zds7GPBffd\"},{\"artist\":\"DaBaby Dan\",\"imageUrl\":null,\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/3cB60Aizh989Sbo6sL3l7R\",\"id\":\"3cB60Aizh989Sbo6sL3l7R\"},{\"artist\":\"DaBabyDracko\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e021385239eca2870a2a56bbdba\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/1Dl9Db1gSCNw1xR2Hg6KjW\",\"id\":\"1Dl9Db1gSCNw1xR2Hg6KjW\"}], \"tracks\":[{\"playbackLink\":\"https:\\/\\/p.scdn.co\\/mp3-preview\\/cc617f669fd1e3ee33a4ac0c66346fefd15286e7?cid=b82ef89345fa42a7893a0f199d64439f\",\"artist\":\"Dua Lipa\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0249caa4fc6f962057ba65576a\",\"name\":\"Levitating (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/463CkQjx2Zk1yXoBuierM9\",\"id\":\"463CkQjx2Zk1yXoBuierM9\"},{\"playbackLink\":null,\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0220e08c8cc23f404d723b5647\",\"name\":\"ROCKSTAR (feat. Roddy Ricch)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/7ytR5pFWmSjzHJIeQkgog4\",\"id\":\"7ytR5pFWmSjzHJIeQkgog4\"},{\"playbackLink\":null,\"artist\":\"Pop Smoke\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0277ada0863603903f57b34369\",\"name\":\"For The Night (feat. Lil Baby & DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/0PvFJmanyNQMseIFrU708S\",\"id\":\"0PvFJmanyNQMseIFrU708S\"},{\"playbackLink\":\"https:\\/\\/p.scdn.co\\/mp3-preview\\/f0c55e74bc02b86ca72cc33890a86bfde1c0afe8?cid=b82ef89345fa42a7893a0f199d64439f\",\"artist\":\"Megan Thee Stallion\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02da256972582b455d46985ba9\",\"name\":\"Cry Baby (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/4aarlAfLKVCTxUDNgbwhjH\",\"id\":\"4aarlAfLKVCTxUDNgbwhjH\"},{\"playbackLink\":null,\"artist\":\"SpotemGottem\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02637d8f4420da84bb0b50e757\",\"name\":\"Beat Box 3 (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/6toQdWWc4noiOk3Eo5mVDS\",\"id\":\"6toQdWWc4noiOk3Eo5mVDS\"}]}");
        assertEquals(f.makeStringSongRequest(searchCorrect), "{\"albums\":[{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0220e08c8cc23f404d723b5647\",\"name\":\"BLAME IT ON BABY\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/623PL2MBg50Br5dLXC9E9e\",\"id\":\"623PL2MBg50Br5dLXC9E9e\"},{\"artist\":\"Dua Lipa\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0249caa4fc6f962057ba65576a\",\"name\":\"Levitating (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/04m06KhJUuwe1Q487puIud\",\"id\":\"04m06KhJUuwe1Q487puIud\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02f2b94b2fda4f08836d6371ba\",\"name\":\"KIRK\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/1NsTSXjVNE7XmZ8PmyW0wl\",\"id\":\"1NsTSXjVNE7XmZ8PmyW0wl\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02e818d05b79be19f4d49f1ebf\",\"name\":\"BLAME IT ON BABY (DELUXE)\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/6SL49HqoUwu2hhfukBAK3Q\",\"id\":\"6SL49HqoUwu2hhfukBAK3Q\"},{\"artist\":\"SpotemGottem\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02637d8f4420da84bb0b50e757\",\"name\":\"Beat Box 3 (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/album\\/6QqwWNpKYbROin3Gq5pIrV\",\"id\":\"6QqwWNpKYbROin3Gq5pIrV\"}], \"artists\":[{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/9fabb3d7dd075894af0b3805c129e433b9104b26\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/4r63FhuTkUYltbVAg5TQnk\",\"id\":\"4r63FhuTkUYltbVAg5TQnk\"},{\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02f51d22db94b7ccbed26870c0\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/7MCrEuHBgUcjP8eMxM2IFC\",\"id\":\"7MCrEuHBgUcjP8eMxM2IFC\"},{\"artist\":\"DaBabyDan\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e028456a317b92641d713968e55\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/0SanfTMHZHJ7zds7GPBffd\",\"id\":\"0SanfTMHZHJ7zds7GPBffd\"},{\"artist\":\"DaBaby Dan\",\"imageUrl\":null,\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/3cB60Aizh989Sbo6sL3l7R\",\"id\":\"3cB60Aizh989Sbo6sL3l7R\"},{\"artist\":\"DaBabyDracko\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e021385239eca2870a2a56bbdba\",\"link\":\"https:\\/\\/open.spotify.com\\/artist\\/1Dl9Db1gSCNw1xR2Hg6KjW\",\"id\":\"1Dl9Db1gSCNw1xR2Hg6KjW\"}], \"tracks\":[{\"playbackLink\":\"https:\\/\\/p.scdn.co\\/mp3-preview\\/cc617f669fd1e3ee33a4ac0c66346fefd15286e7?cid=b82ef89345fa42a7893a0f199d64439f\",\"artist\":\"Dua Lipa\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0249caa4fc6f962057ba65576a\",\"name\":\"Levitating (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/463CkQjx2Zk1yXoBuierM9\",\"id\":\"463CkQjx2Zk1yXoBuierM9\"},{\"playbackLink\":null,\"artist\":\"DaBaby\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0220e08c8cc23f404d723b5647\",\"name\":\"ROCKSTAR (feat. Roddy Ricch)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/7ytR5pFWmSjzHJIeQkgog4\",\"id\":\"7ytR5pFWmSjzHJIeQkgog4\"},{\"playbackLink\":null,\"artist\":\"Pop Smoke\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e0277ada0863603903f57b34369\",\"name\":\"For The Night (feat. Lil Baby & DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/0PvFJmanyNQMseIFrU708S\",\"id\":\"0PvFJmanyNQMseIFrU708S\"},{\"playbackLink\":\"https:\\/\\/p.scdn.co\\/mp3-preview\\/f0c55e74bc02b86ca72cc33890a86bfde1c0afe8?cid=b82ef89345fa42a7893a0f199d64439f\",\"artist\":\"Megan Thee Stallion\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02da256972582b455d46985ba9\",\"name\":\"Cry Baby (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/4aarlAfLKVCTxUDNgbwhjH\",\"id\":\"4aarlAfLKVCTxUDNgbwhjH\"},{\"playbackLink\":null,\"artist\":\"SpotemGottem\",\"imageUrl\":\"https:\\/\\/i.scdn.co\\/image\\/ab67616d00001e02637d8f4420da84bb0b50e757\",\"name\":\"Beat Box 3 (feat. DaBaby)\",\"link\":\"https:\\/\\/open.spotify.com\\/track\\/6toQdWWc4noiOk3Eo5mVDS\",\"id\":\"6toQdWWc4noiOk3Eo5mVDS\"}]}");
    }

    /*@Test
    public void getResponseTest_returnsFalse() {

    }*/

    //private boolean trySearch(String response)
}