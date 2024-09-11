package com.market.bc.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class EthMynft extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b50604051620018d5380380620018d583398101604052805160208201519082019101620000677f01ffc9a700000000000000000000000000000000000000000000000000000000640100000000620000e1810204565b6200009b7f80ac58cd00000000000000000000000000000000000000000000000000000000640100000000620000e1810204565b8151620000b09060069060208501906200014e565b508051620000c69060079060208401906200014e565b505060058054600160a060020a0319163317905550620001f3565b7fffffffff0000000000000000000000000000000000000000000000000000000080821614156200011157600080fd5b7fffffffff00000000000000000000000000000000000000000000000000000000166000908152602081905260409020805460ff19166001179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200019157805160ff1916838001178555620001c1565b82800160010185558215620001c1579182015b82811115620001c1578251825591602001919060010190620001a4565b50620001cf929150620001d3565b5090565b620001f091905b80821115620001cf5760008155600101620001da565b90565b6116d280620002036000396000f3006080604052600436106101275763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166301ffc9a7811461012c57806302d05d3f1461017757806306fdde03146101a8578063081812fc14610232578063095ea7b31461024a5780631af716ba1461027057806323b872dd146102df5780633e23f5861461030957806342842e0e146103785780636352211e146103a25780636ab4dc48146103ba57806370a08231146104675780638940aebe1461049a5780638a02f6e5146104b257806395d89b4114610597578063a22cb465146105ac578063ac69a69b146105d2578063b88d4fde14610630578063c87b56dd1461069f578063d69f2b5314610795578063e932ad19146107f3578063e985e9c51461089a575b600080fd5b34801561013857600080fd5b506101637bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19600435166108c1565b604080519115158252519081900360200190f35b34801561018357600080fd5b5061018c6108f5565b60408051600160a060020a039092168252519081900360200190f35b3480156101b457600080fd5b506101bd610905565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101f75781810151838201526020016101df565b50505050905090810190601f1680156102245780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561023e57600080fd5b5061018c60043561099b565b34801561025657600080fd5b5061026e600160a060020a03600435166024356109cd565b005b34801561027c57600080fd5b50604080516020601f60643560048181013592830184900484028501840190955281845261026e94600160a060020a038135811695602480359092169560443595369560849401918190840183828082843750949750610a839650505050505050565b3480156102eb57600080fd5b5061026e600160a060020a0360043581169060243516604435610ab4565b34801561031557600080fd5b50604080516020601f60643560048181013592830184900484028501840190955281845261026e94600160a060020a038135811695602480359092169560443595369560849401918190840183828082843750949750610b429650505050505050565b34801561038457600080fd5b5061026e600160a060020a0360043581169060243516604435610b65565b3480156103ae57600080fd5b5061018c600435610b86565b3480156103c657600080fd5b50604080516020601f60643560048181013592830184900484028501840190955281845261026e94600160a060020a03813581169560248035909216956044359536956084940191819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750610bb09650505050505050565b34801561047357600080fd5b50610488600160a060020a0360043516610bf2565b60408051918252519081900360200190f35b3480156104a657600080fd5b506101bd600435610c25565b3480156104be57600080fd5b50604080516020600460443581810135601f810184900484028501840190955284845261026e9482359460248035600160a060020a03169536959460649492019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750610cc69650505050505050565b3480156105a357600080fd5b506101bd610d3a565b3480156105b857600080fd5b5061026e600160a060020a03600435166024351515610d9b565b3480156105de57600080fd5b5060408051602060046024803582810135601f810185900485028601850190965285855261026e958335953695604494919390910191908190840183828082843750949750610e1f9650505050505050565b34801561063c57600080fd5b50604080516020601f60643560048181013592830184900484028501840190955281845261026e94600160a060020a038135811695602480359092169560443595369560849401918190840183828082843750949750610e599650505050505050565b3480156106ab57600080fd5b506106b7600435610e7b565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156106f85781810151838201526020016106e0565b50505050905090810190601f1680156107255780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610758578181015183820152602001610740565b50505050905090810190601f1680156107855780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b3480156107a157600080fd5b5060408051602060046024803582810135601f810185900485028601850190965285855261026e95833595369560449491939091019190819084018382808284375094975061104f9650505050505050565b3480156107ff57600080fd5b50604080516020600460443581810135601f810184900484028501840190955284845261026e9482359460248035600160a060020a03169536959460649492019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506110839650505050505050565b3480156108a657600080fd5b50610163600160a060020a03600435811690602435166110d7565b7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191660009081526020819052604090205460ff1690565b600554600160a060020a03165b90565b60068054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156109915780601f1061096657610100808354040283529160200191610991565b820191906000526020600020905b81548152906001019060200180831161097457829003601f168201915b5050505050905090565b60006109a682611105565b15156109b157600080fd5b50600090815260026020526040902054600160a060020a031690565b60006109d882610b86565b9050600160a060020a0383811690821614156109f357600080fd5b33600160a060020a0382161480610a0f5750610a0f81336110d7565b1515610a1a57600080fd5b600082815260026020526040808220805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0387811691821790925591518593918516917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591a4505050565b610a8e848484610ab4565b60008281526009602090815260409091208251610aad9284019061160e565b5050505050565b610abe3382611122565b1515610ac957600080fd5b600160a060020a0382161515610ade57600080fd5b610ae88382611181565b610af283826111f2565b610afc8282611288565b8082600160a060020a031684600160a060020a03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a4505050565b610b5f848484602060405190810160405280600081525085610bb0565b50505050565b610b818383836020604051908101604052806000815250610e59565b505050565b600081815260016020526040812054600160a060020a0316801515610baa57600080fd5b92915050565b610bbb858585610ab4565b60008381526009602090815260409091208251610bda9284019061160e565b50610be785858585611318565b1515610aad57600080fd5b6000600160a060020a0382161515610c0957600080fd5b50600160a060020a031660009081526003602052604090205490565b60008181526009602090815260409182902080548351601f6002600019610100600186161502019093169290920491820184900484028101840190945280845260609392830182828015610cba5780601f10610c8f57610100808354040283529160200191610cba565b820191906000526020600020905b815481529060010190602001808311610c9d57829003601f168201915b50505050509050919050565b600554600160a060020a03163314610cdd57600080fd5b610ce685611105565b15610cf057600080fd5b600160a060020a0384161515610d0557600080fd5b610d0f848661149a565b60008581526009602090815260409091208251610d2e9284019061160e565b50610aad8584846114f5565b60078054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156109915780601f1061096657610100808354040283529160200191610991565b600160a060020a038216331415610db157600080fd5b336000818152600460209081526040808320600160a060020a03871680855290835292819020805460ff1916861515908117909155815190815290519293927f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31929181900390910190a35050565b610e293383611122565b1515610e3457600080fd5b60008281526008602090815260409091208251610b819260019092019184019061160e565b610e64848484610ab4565b610e7084848484611318565b1515610b5f57600080fd5b606080610e8783611105565b1515610f1a57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602f60248201527f4552433732314d657461646174613a2055524920717565727920666f72206e6f60448201527f6e6578697374656e7420746f6b656e0000000000000000000000000000000000606482015290519081900360840190fd5b600083815260086020908152604091829020805483516002600180841615610100026000190190931604601f8101859004850282018501909552848152919390840192849190830182828015610fb15780601f10610f8657610100808354040283529160200191610fb1565b820191906000526020600020905b815481529060010190602001808311610f9457829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529597508694509250840190508282801561103f5780601f106110145761010080835404028352916020019161103f565b820191906000526020600020905b81548152906001019060200180831161102257829003601f168201915b5050505050905091509150915091565b6110593383611122565b151561106457600080fd5b60008281526009602090815260409091208251610b819284019061160e565b600554600160a060020a0316331461109a57600080fd5b6110a384611105565b156110ad57600080fd5b600160a060020a03831615156110c257600080fd5b6110cc838561149a565b610b5f8483836114f5565b600160a060020a03918216600090815260046020908152604080832093909416825291909152205460ff1690565b600090815260016020526040902054600160a060020a0316151590565b60008061112e83610b86565b905080600160a060020a031684600160a060020a03161480611169575083600160a060020a031661115e8461099b565b600160a060020a0316145b80611179575061117981856110d7565b949350505050565b81600160a060020a031661119482610b86565b600160a060020a0316146111a757600080fd5b600081815260026020526040902054600160a060020a0316156111ee576000818152600260205260409020805473ffffffffffffffffffffffffffffffffffffffff191690555b5050565b81600160a060020a031661120582610b86565b600160a060020a03161461121857600080fd5b600160a060020a03821660009081526003602052604090205461124290600163ffffffff6115d616565b600160a060020a03909216600090815260036020908152604080832094909455918152600190915220805473ffffffffffffffffffffffffffffffffffffffff19169055565b600081815260016020526040902054600160a060020a0316156112aa57600080fd5b6000818152600160208181526040808420805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03881690811790915584526003909152909120546112f8916115ed565b600160a060020a0390921660009081526003602052604090209190915550565b60008061132d85600160a060020a0316611606565b151561133c5760019150611491565b6040517f150b7a020000000000000000000000000000000000000000000000000000000081523360048201818152600160a060020a03898116602485015260448401889052608060648501908152875160848601528751918a169463150b7a0294938c938b938b93909160a490910190602085019080838360005b838110156113cf5781810151838201526020016113b7565b50505050905090810190601f1680156113fc5780820380516001836020036101000a031916815260200191505b5095505050505050602060405180830381600087803b15801561141e57600080fd5b505af1158015611432573d6000803e3d6000fd5b505050506040513d602081101561144857600080fd5b50517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1981167f150b7a020000000000000000000000000000000000000000000000000000000014925090505b50949350505050565b600160a060020a03821615156114af57600080fd5b6114b98282611288565b6040518190600160a060020a038416906000907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b6114fe83611105565b151561159157604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602c60248201527f4552433732314d657461646174613a2055524920736574206f66206e6f6e657860448201527f697374656e7420746f6b656e0000000000000000000000000000000000000000606482015290519081900360840190fd5b600083815260086020908152604090912083516115b09285019061160e565b5060008381526008602090815260409091208251610b5f9260019092019184019061160e565b600080838311156115e657600080fd5b5050900390565b6000828201838110156115ff57600080fd5b9392505050565b6000903b1190565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061164f57805160ff191683800117855561167c565b8280016001018555821561167c579182015b8281111561167c578251825591602001919060010190611661565b5061168892915061168c565b5090565b61090291905b8082111561168857600081556001016116925600a165627a7a723058209b75072f1ba3ece0d59c42a5dc9641e0667c4b2b38afb26438b3bb3d4d6857d80029";

    protected EthMynft(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EthMynft(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Transfer", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Transfer", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Approval", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Approval", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ApprovalForAll", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalForAllEventResponse> approvalForAllEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ApprovalForAll", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<Boolean> supportsInterface(byte[] interfaceId) {
        Function function = new Function("supportsInterface", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> creator() {
        Function function = new Function("creator", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> name() {
        Function function = new Function("name", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getApproved(BigInteger tokenId) {
        Function function = new Function("getApproved", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        Function function = new Function(
                "approve", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId, String pubKey) {
        Function function = new Function(
                "transferFrom", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.Utf8String(pubKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        Function function = new Function(
                "transferFrom", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, String pubKey) {
        Function function = new Function(
                "safeTransferFrom", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.Utf8String(pubKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        Function function = new Function(
                "safeTransferFrom", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> ownerOf(BigInteger tokenId) {
        Function function = new Function("ownerOf", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] _data, String pubKey) {
        Function function = new Function(
                "safeTransferFrom", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(_data), 
                new org.web3j.abi.datatypes.Utf8String(pubKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> balanceOf(String owner) {
        Function function = new Function("balanceOf", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> publicKey(BigInteger tokenId) {
        Function function = new Function("publicKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> mintWithPubkey(BigInteger tokenId, String to, String files, String auth, String pubkey) {
        Function function = new Function(
                "mintWithPubkey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.Utf8String(files), 
                new org.web3j.abi.datatypes.Utf8String(auth), 
                new org.web3j.abi.datatypes.Utf8String(pubkey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> symbol() {
        Function function = new Function("symbol", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setApprovalForAll(String to, Boolean approved) {
        Function function = new Function(
                "setApprovalForAll", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.Bool(approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setTokenURIAuth(BigInteger tokenId, String auth) {
        Function function = new Function(
                "setTokenURIAuth", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.Utf8String(auth)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] _data) {
        Function function = new Function(
                "safeTransferFrom", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<String, String>> tokenURI(BigInteger tokenId) {
        final Function function = new Function("tokenURI", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> setPublicKey(BigInteger tokenId, String pubKey) {
        Function function = new Function(
                "setPublicKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.Utf8String(pubKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> mint(BigInteger tokenId, String to, String files, String auth) {
        Function function = new Function(
                "mint", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.Utf8String(files), 
                new org.web3j.abi.datatypes.Utf8String(auth)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isApprovedForAll(String owner, String operator) {
        Function function = new Function("isApprovedForAll", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(owner), 
                new org.web3j.abi.datatypes.Address(operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static RemoteCall<EthMynft> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String name, String symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(symbol)));
        return deployRemoteCall(EthMynft.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<EthMynft> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String name, String symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(symbol)));
        return deployRemoteCall(EthMynft.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static EthMynft load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EthMynft(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static EthMynft load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EthMynft(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TransferEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }

    public static class ApprovalEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }
}
