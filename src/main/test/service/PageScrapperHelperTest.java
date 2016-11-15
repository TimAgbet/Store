package service;

import com.jaunt.Element;
import com.jaunt.NotFound;
import domain.ProductDetails;
import domain.QueryType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PageScrapperHelperTest {

    private PageScrapperHelper pageScrapperHelper = new PageScrapperHelper(){
        @Override
        protected String getTitle(Element element) {
            return "Test Title";
        }

        @Override
        public String getLinkData(Element element, String findData, QueryType queryType) {
            return "5.600000000000000000000";
        }

        @Override
        public int getLinkedHTMlSize(String url) {
            return 8;
        }
    };

    private Element element = mock(Element.class);

    @Test
    public void shouldGetCorrectProductDetails() throws NotFound {
        // Given
        given(element.getElement(0)).willReturn(Mockito.any(Element.class));

        // When
        ProductDetails result = pageScrapperHelper.getProductDetails(element);

        //Then
        assertEquals(result.getTitle(),"Test Title");
        assertEquals(result.getProductText(),"5.600000000000000000000");
        assertEquals(result.getSize(),"8 kb");
    }

}