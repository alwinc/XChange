package org.knowm.xchange.bitfinex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.BitfinexAuthenticated;
import org.knowm.xchange.bitfinex.v1.BitfinexHmacPostBodyDigest;
import org.knowm.xchange.bitfinex.v2.BitfinexHmacSignature;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class BitfinexBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BitfinexAuthenticated bitfinex;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  protected final org.knowm.xchange.bitfinex.v2.BitfinexAuthenticated bitfinexV2;
  protected final BitfinexHmacSignature signatureV2;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexBaseService(Exchange exchange) {

    super(exchange);

    this.bitfinex =
        ExchangeRestProxyBuilder.forInterface(
                BitfinexAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitfinexHmacPostBodyDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new BitfinexPayloadDigest();

    this.bitfinexV2 =
        ExchangeRestProxyBuilder.forInterface(
                org.knowm.xchange.bitfinex.v2.BitfinexAuthenticated.class,
                exchange.getExchangeSpecification())
            .build();
    this.signatureV2 =
        BitfinexHmacSignature.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}