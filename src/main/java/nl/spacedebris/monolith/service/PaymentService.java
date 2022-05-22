package nl.spacedebris.monolith.service;

import lombok.extern.slf4j.Slf4j;
import nl.spacedebris.monolith.model.Order;
import nl.spacedebris.monolith.model.Payment;
import nl.spacedebris.monolith.model.enums.OrderStatus;
import nl.spacedebris.monolith.model.enums.PaymentStatus;
import nl.spacedebris.monolith.repository.OrderRepository;
import nl.spacedebris.monolith.repository.PaymentRepository;
import nl.spacedebris.monolith.repository.web.dto.PaymentDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class PaymentService {

    @Inject
    PaymentRepository paymentRepository;
    @Inject
    OrderRepository orderRepository;

    public static PaymentDto mapToDto(Payment payment, Long orderId) {
        if (payment != null) {
            return new PaymentDto(
                    payment.getId(),
                    payment.getPaypalPaymentId(),
                    payment.getStatus().name(),
                    orderId
            );
        }
        return null;
    }

    public List<PaymentDto> findByPriceRange(Double max) {
        return this.paymentRepository
                .findAllByAmountBetween(BigDecimal.ZERO, BigDecimal.valueOf(max))
                .stream()
                .map(payment -> mapToDto(payment, findOrderByPaymentId(payment.getId()).getId()))
                .collect(Collectors.toList());
    }

    public List<PaymentDto> findAll() {
        log.debug("Request to get all Payments");
        return this.paymentRepository.findAll()
                .stream()
                .map(payment -> findById(payment.getId()))
                .collect(Collectors.toList());
    }

    public PaymentDto findById(Long id) {
        log.debug("Request to get Payment : {}", id);
        var order = findOrderByPaymentId(id);

        return this.paymentRepository
                .findById(id)
                .map(payment -> mapToDto(payment, order.getId()))
                .orElse(null);
    }

    public PaymentDto create(PaymentDto paymentDto) {
        log.debug("Request to create Payment : {}", paymentDto);

        var order =
                this.orderRepository
                        .findById(paymentDto.getOrderId())
                        .orElseThrow(() -> new IllegalStateException("The Order does not exist!"));

        order.setStatus(OrderStatus.PAID);

        var payment = this.paymentRepository.saveAndFlush(new Payment(
                paymentDto.getPaypalPaymentId(),
                PaymentStatus.valueOf(paymentDto.getStatus()),
                order.getPrice()
        ));

        this.orderRepository.saveAndFlush(order);

        return mapToDto(payment, order.getId());
    }

    private Order findOrderByPaymentId(Long id) {
        return this.orderRepository.findByPaymentId(id)
                .orElseThrow(() -> new IllegalStateException("No Order exists for the Payment ID " + id));
    }

    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        this.paymentRepository.deleteById(id);
    }
}