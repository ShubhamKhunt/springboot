package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.*;
import basic.springboot.simple.exception.InternalServerException;
import basic.springboot.simple.exception.NoDataFoundException;
import basic.springboot.simple.model.DTO.SalesOrderDTO;
import basic.springboot.simple.repository.*;
import basic.springboot.simple.service.BaseService;
import basic.springboot.simple.service.EmailService;
import basic.springboot.simple.service.SalesOrderService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service Interfce Impl : SalesOrder
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SalesOrderEntityManagerRepository salesOrderEntityManagerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SalesOrder save(SalesOrderDTO salesOrderDTO) {

        try{
            // Get Address
            Optional<Address> address = addressRepository.findById(Integer.valueOf(salesOrderDTO.getAddressId()));

            if(!address.isPresent()){
                throw new NoDataFoundException("Address not found");
            }

            // Get Contact
            Optional<Contact> contact = contactRepository.findById(Integer.parseInt(salesOrderDTO.getContactId()));

            if(!contact.isPresent()){
                throw new NoDataFoundException("Contact not found");
            }

            // Get Current User
            User user = baseService.getCurrentUser();

            if(Objects.isNull(user)){
                throw new NoDataFoundException("Invalid User");
            }

            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            SalesOrder salesOrder = modelMapper.map(salesOrderDTO, SalesOrder.class);

            Contact tmpContact = contact.get();

            salesOrder.setShippingName(tmpContact.getFirstName() + " " + tmpContact.getLastName());
            salesOrder.setShippingEmail(tmpContact.getEmail());
            salesOrder.setShippingContact(tmpContact.getContactNo());
            salesOrder.setAddress(address.get());
            salesOrder.setContact(contact.get());
            salesOrder.setUser(user);
            salesOrder.setTenant(baseService.getTenant());
            salesOrder.setCreated(new Date());
            salesOrder.setModified(new Date());

            List<SalesOrderItem> soItemList = new ArrayList<>();
            AtomicReference<Double> orderAmount = new AtomicReference<>(0.0);
            salesOrderDTO.getSalesOrderItems().stream().forEach(soItemDto -> {

                Optional<Product> product = productRepository.findById(Integer.valueOf(soItemDto.getProdctId()));

                // Validate Product
                if(!product.isPresent()){
                    throw new NoDataFoundException("Product Not Found");
                }

                SalesOrderItem salesOrderItem = new SalesOrderItem();
                salesOrderItem.setName(product.get().getName());
                salesOrderItem.setSku(product.get().getSku());
                salesOrderItem.setQtyOrdered(Integer.parseInt(soItemDto.getQtyOrdered()));
                salesOrderItem.setUnitPrice(Double.parseDouble(soItemDto.getUnitPrice()));
                salesOrderItem.setTotalAmount(Integer.parseInt(soItemDto.getQtyOrdered()) * Double.parseDouble(soItemDto.getUnitPrice()));
                salesOrderItem.setSalesOrder(salesOrder);
                salesOrderItem.setCreated(new Date());
                salesOrderItem.setModified(new Date());

                orderAmount.updateAndGet(v -> v + Integer.parseInt(soItemDto.getQtyOrdered()) * Double.parseDouble(soItemDto.getUnitPrice()));

                soItemList.add(salesOrderItem);
            });

            salesOrder.setOrderAmount(orderAmount.get());
            salesOrder.setSalesOrderItems(soItemList);

            // salesOrderRepository.save(salesOrder);
            salesOrderEntityManagerRepository.save(salesOrder);

            System.out.println("Order Number : " + salesOrder.getOrderNumber());

            // Send Email to End User
            JSONObject jsonData = new JSONObject();
            jsonData.put("name", tmpContact.getFirstName() + " " + tmpContact.getLastName());
            jsonData.put("Name", tmpContact.getFirstName() + " " + tmpContact.getLastName());
            jsonData.put("order_number", salesOrder.getOrderNumber());

            JSONObject identity = new JSONObject();
            identity.put("id", tmpContact.getEmail());
            identity.put("email", tmpContact.getEmail());
            identity.put("username", tmpContact.getEmail());
            identity.put("Username", tmpContact.getEmail());
            identity.put("name", tmpContact.getFirstName() + " " + tmpContact.getLastName());
            identity.put("Name", tmpContact.getFirstName() + " " + tmpContact.getLastName());

            emailService.sendMail(identity, jsonData, EmailServiceImpl.EventType.ORDER_PLACED);

            return salesOrder;
        } catch (Exception e){
            // e.printStackTrace();
            throw new InternalServerException("Something went wrong while persisting object!!");
        }
    }
}
