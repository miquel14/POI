package com.worldline.template.clean.presenter;

import com.worldline.template.clean.view.IView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * PresenterTest
 */
public class PresenterTest {

    Presenter presenter;

    @Before
    public void setUp() {
        presenter = mock(Presenter.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullViewPresenterShouldThrowException() {
        presenter.setView(null);
    }

    @Test
    public void testWithObjectPresenterHappyPath() {
        presenter.setView((IView) new Object());
        presenter.start();

        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullViewPresenterShouldThrowExceptionOnCheck() {
        presenter.start();
    }
}
