package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository", itemRepository);
    }

    @Test
    public void testGetAllItems(){
        List<Item> items = new ArrayList<>();
        Item item1 = new Item(1L, "Round", BigDecimal.valueOf(2.99), "1");
        Item item2 = new Item(2L, "Square", BigDecimal.valueOf(1.99), "2");
        items.add(item1);
        items.add(item2);

        when(itemRepository.findAll()).thenReturn(items);
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(items, response.getBody());
    }

    @Test
    public void testFindItems() {
        Item item1 = new Item(1L, "Round", BigDecimal.valueOf(2.99), "1");
        Item item2 = new Item(2L, "Square", BigDecimal.valueOf(1.99), "2");

        // find by id
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item2));
        ResponseEntity<Item> responseID = itemController.getItemById(2L);
        assertNotNull(responseID);
        assertEquals(200, responseID.getStatusCodeValue());
        assertEquals(item2, responseID.getBody());

        // find by name
        when(itemRepository.findByName("Round")).thenReturn(Arrays.asList(item1));
        ResponseEntity<List<Item>> responseByName = itemController.getItemsByName("Round");
        assertNotNull(responseByName);
        assertEquals(200, responseByName.getStatusCodeValue());
        assertEquals(Arrays.asList(item1), responseByName.getBody());

    }

    @Test
    public void testFindItemsFail() {
        // find by id
        ResponseEntity<Item> responseID = itemController.getItemById(3L);
        assertNotNull(responseID);
        assertEquals(404, responseID.getStatusCodeValue());
        assertNull(responseID.getBody());

        // find by name
        ResponseEntity<List<Item>> responseByName = itemController.getItemsByName("Triangle");
        assertNotNull(responseByName);
        assertEquals(404, responseByName.getStatusCodeValue());
        assertNull(responseByName.getBody());
    }

}
