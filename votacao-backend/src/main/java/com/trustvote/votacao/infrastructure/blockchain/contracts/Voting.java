package com.trustvote.votacao.infrastructure.blockchain.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.3.
 */
@SuppressWarnings("rawtypes")
public class Voting extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50604051610fd1380380610fd183398101604081905261002f916100bc565b6001600160a01b0381166100895760405162461bcd60e51b815260206004820152601f60248201527f6261636b656e645369676e6572206e616f20706f646520736572206e756c6f00604482015260640160405180910390fd5b60008054336001600160a01b031991821617909155600180549091166001600160a01b03929092169190911790556100ec565b6000602082840312156100ce57600080fd5b81516001600160a01b03811681146100e557600080fd5b9392505050565b610ed6806100fb6000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c806335b8e8201161005b57806335b8e820146100c857806365d65e86146100e957806386cbaed814610114578063f851a4401461012757600080fd5b80630a6ac2be146100825780632d35a8a2146100975780632e6997fe146100b3575b600080fd5b610095610090366004610a59565b61013a565b005b6100a060045481565b6040519081526020015b60405180910390f35b6100bb610359565b6040516100aa9190610b24565b6100db6100d6366004610b9a565b6104b7565b6040516100aa929190610bb3565b6001546100fc906001600160a01b031681565b6040516001600160a01b0390911681526020016100aa565b610095610122366004610bd5565b6105aa565b6000546100fc906001600160a01b031681565b6000546001600160a01b031633146101a35760405162461bcd60e51b815260206004820152602160248201527f4170656e6173206f2061646d696e20706f6465206578656375746172206973736044820152606f60f81b60648201526084015b60405180910390fd5b60008251116102005760405162461bcd60e51b8152602060048201526024808201527f4e6f6d6520646f2063616e64696461746f206e616f20706f6465207365722076604482015263617a696f60e01b606482015260840161019a565b6000826040516020016102139190610c3a565b604051602081830303815290604052805190602001209050600061026d610267837f19457468657265756d205369676e6564204d6573736167653a0a3332000000006000908152601c91909152603c902090565b846107a3565b6001549091506001600160a01b038083169116146102c45760405162461bcd60e51b8152602060048201526014602482015273417373696e617475726120696e76616c6964612160601b604482015260640161019a565b6040805180820182528581526000602080830182905260045482526002905291909120815181906102f59082610cd9565b50602082015181600101559050507fe83b2a43e7e82d975c8a0a6d2f045153c869e111136a34d1889ab7b598e396a360045485604051610336929190610d99565b60405180910390a16004805490600061034e83610dba565b919050555050505050565b6060600060045467ffffffffffffffff811115610378576103786109a6565b6040519080825280602002602001820160405280156103be57816020015b6040805180820190915260608152600060208201528152602001906001900390816103965790505b50905060005b6004548110156104b1576000818152600260205260409081902081518083019092528054829082906103f590610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461042190610c56565b801561046e5780601f106104435761010080835404028352916020019161046e565b820191906000526020600020905b81548152906001019060200180831161045157829003601f168201915b5050505050815260200160018201548152505082828151811061049357610493610de1565b602002602001018190525080806104a990610dba565b9150506103c4565b50919050565b6060600060045483106105015760405162461bcd60e51b815260206004820152601260248201527143616e64696461746f20696e76616c69646f60701b604482015260640161019a565b600083815260026020526040902060018101548154829061052190610c56565b80601f016020809104026020016040519081016040528092919081815260200182805461054d90610c56565b801561059a5780601f1061056f5761010080835404028352916020019161059a565b820191906000526020600020905b81548152906001019060200180831161057d57829003601f168201915b5050505050915091509150915091565b6001600160a01b03831660009081526003602052604090205460ff16156106035760405162461bcd60e51b815260206004820152600d60248201526c566f6365206a6120766f746f7560981b604482015260640161019a565b60045482106106495760405162461bcd60e51b815260206004820152601260248201527143616e64696461746f20696e76616c69646f60701b604482015260640161019a565b60408051606085901b6bffffffffffffffffffffffff191660208083019190915282516014818403018152603490920190925280519101207f19457468657265756d205369676e6564204d6573736167653a0a3332000000006000908152601c829052603c81206106b990610267565b6001549091506001600160a01b038083169116146107105760405162461bcd60e51b8152602060048201526014602482015273417373696e617475726120696e76616c6964612160601b604482015260640161019a565b600084815260026020526040812060010180549161072d83610dba565b90915550506001600160a01b0385166000818152600360209081526040808320805460ff1916600117905587835260029091529081902090517f497ac9c2d8b6e7ea7204365157d9642f36d2b6125352bfa6d0a73328142d0bd99161079491889190610df7565b60405180910390a25050505050565b6000806000806107b386866107cd565b9250925092506107c3828261081a565b5090949350505050565b600080600083516041036108075760208401516040850151606086015160001a6107f9888285856108d7565b955095509550505050610813565b50508151600091506002905b9250925092565b600082600381111561082e5761082e610e8a565b03610837575050565b600182600381111561084b5761084b610e8a565b036108695760405163f645eedf60e01b815260040160405180910390fd5b600282600381111561087d5761087d610e8a565b0361089e5760405163fce698f760e01b81526004810182905260240161019a565b60038260038111156108b2576108b2610e8a565b036108d3576040516335e2f38360e21b81526004810182905260240161019a565b5050565b600080807f7fffffffffffffffffffffffffffffff5d576e7357a4501ddfe92f46681b20a0841115610912575060009150600390508261099c565b604080516000808252602082018084528a905260ff891692820192909252606081018790526080810186905260019060a0016020604051602081039080840390855afa158015610966573d6000803e3d6000fd5b5050604051601f1901519150506001600160a01b0381166109925750600092506001915082905061099c565b9250600091508190505b9450945094915050565b634e487b7160e01b600052604160045260246000fd5b600067ffffffffffffffff808411156109d7576109d76109a6565b604051601f8501601f19908116603f011681019082821181831017156109ff576109ff6109a6565b81604052809350858152868686011115610a1857600080fd5b858560208301376000602087830101525050509392505050565b600082601f830112610a4357600080fd5b610a52838335602085016109bc565b9392505050565b60008060408385031215610a6c57600080fd5b823567ffffffffffffffff80821115610a8457600080fd5b818501915085601f830112610a9857600080fd5b610aa7868335602085016109bc565b93506020850135915080821115610abd57600080fd5b50610aca85828601610a32565b9150509250929050565b60005b83811015610aef578181015183820152602001610ad7565b50506000910152565b60008151808452610b10816020860160208601610ad4565b601f01601f19169290920160200192915050565b60006020808301818452808551808352604092508286019150828160051b87010184880160005b83811015610b8c57888303603f1901855281518051878552610b6f88860182610af8565b918901519489019490945294870194925090860190600101610b4b565b509098975050505050505050565b600060208284031215610bac57600080fd5b5035919050565b604081526000610bc66040830185610af8565b90508260208301529392505050565b600080600060608486031215610bea57600080fd5b83356001600160a01b0381168114610c0157600080fd5b925060208401359150604084013567ffffffffffffffff811115610c2457600080fd5b610c3086828701610a32565b9150509250925092565b60008251610c4c818460208701610ad4565b9190910192915050565b600181811c90821680610c6a57607f821691505b6020821081036104b157634e487b7160e01b600052602260045260246000fd5b601f821115610cd457600081815260208120601f850160051c81016020861015610cb15750805b601f850160051c820191505b81811015610cd057828155600101610cbd565b5050505b505050565b815167ffffffffffffffff811115610cf357610cf36109a6565b610d0781610d018454610c56565b84610c8a565b602080601f831160018114610d3c5760008415610d245750858301515b600019600386901b1c1916600185901b178555610cd0565b600085815260208120601f198616915b82811015610d6b57888601518255948401946001909101908401610d4c565b5085821015610d895787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b828152604060208201526000610db26040830184610af8565b949350505050565b600060018201610dda57634e487b7160e01b600052601160045260246000fd5b5060010190565b634e487b7160e01b600052603260045260246000fd5b8281526000602060408184015260008454610e1181610c56565b8060408701526060600180841660008114610e335760018114610e4d57610e7b565b60ff1985168984015283151560051b890183019550610e7b565b896000528660002060005b85811015610e735781548b8201860152908301908801610e58565b8a0184019650505b50939998505050505050505050565b634e487b7160e01b600052602160045260246000fdfea264697066735822122004146f1b7b7ff5e4b6123c820761b6d220ca73664f5fbf3b847c27a7dfbdfc3c64736f6c63430008140033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDCANDIDATE = "addCandidate";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_BACKENDSIGNER = "backendSigner";

    public static final String FUNC_CANDIDATESCOUNT = "candidatesCount";

    public static final String FUNC_GETALLCANDIDATES = "getAllCandidates";

    public static final String FUNC_GETCANDIDATE = "getCandidate";

    public static final String FUNC_VOTE = "vote";

    public static final Event CANDIDATEADDED_EVENT = new Event("CandidateAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event VOTED_EVENT = new Event("Voted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Voting(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Voting(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Voting(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Voting(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<CandidateAddedEventResponse> getCandidateAddedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CANDIDATEADDED_EVENT, transactionReceipt);
        ArrayList<CandidateAddedEventResponse> responses = new ArrayList<CandidateAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CandidateAddedEventResponse typedResponse = new CandidateAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.candidateId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CandidateAddedEventResponse getCandidateAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CANDIDATEADDED_EVENT, log);
        CandidateAddedEventResponse typedResponse = new CandidateAddedEventResponse();
        typedResponse.log = log;
        typedResponse.candidateId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<CandidateAddedEventResponse> candidateAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCandidateAddedEventFromLog(log));
    }

    public Flowable<CandidateAddedEventResponse> candidateAddedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANDIDATEADDED_EVENT));
        return candidateAddedEventFlowable(filter);
    }

    public static List<VotedEventResponse> getVotedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTED_EVENT, transactionReceipt);
        ArrayList<VotedEventResponse> responses = new ArrayList<VotedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotedEventResponse typedResponse = new VotedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.candidateId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.candidateName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotedEventResponse getVotedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTED_EVENT, log);
        VotedEventResponse typedResponse = new VotedEventResponse();
        typedResponse.log = log;
        typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.candidateId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.candidateName = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<VotedEventResponse> votedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVotedEventFromLog(log));
    }

    public Flowable<VotedEventResponse> votedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTED_EVENT));
        return votedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addCandidate(String _name, byte[] signature) {
        final Function function = new Function(
                FUNC_ADDCANDIDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> backendSigner() {
        final Function function = new Function(FUNC_BACKENDSIGNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> candidatesCount() {
        final Function function = new Function(FUNC_CANDIDATESCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getAllCandidates() {
        final Function function = new Function(FUNC_GETALLCANDIDATES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Candidate>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<String, BigInteger>> getCandidate(BigInteger _candidateId) {
        final Function function = new Function(FUNC_GETCANDIDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_candidateId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<String, BigInteger>>(function,
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> vote(String voterAddress, BigInteger _candidateId,
            byte[] signature) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, voterAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_candidateId), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Voting load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Voting(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Voting load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Voting(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Voting load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Voting(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Voting load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Voting(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Voting> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _backendSigner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _backendSigner)));
        return deployRemoteCall(Voting.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<Voting> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String _backendSigner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _backendSigner)));
        return deployRemoteCall(Voting.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Voting> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _backendSigner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _backendSigner)));
        return deployRemoteCall(Voting.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Voting> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, String _backendSigner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _backendSigner)));
        return deployRemoteCall(Voting.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class Candidate extends DynamicStruct {
        public String name;

        public BigInteger voteCount;

        public Candidate(String name, BigInteger voteCount) {
            super(new org.web3j.abi.datatypes.Utf8String(name), 
                    new org.web3j.abi.datatypes.generated.Uint256(voteCount));
            this.name = name;
            this.voteCount = voteCount;
        }

        public Candidate(Utf8String name, Uint256 voteCount) {
            super(name, voteCount);
            this.name = name.getValue();
            this.voteCount = voteCount.getValue();
        }
    }

    public static class CandidateAddedEventResponse extends BaseEventResponse {
        public BigInteger candidateId;

        public String name;
    }

    public static class VotedEventResponse extends BaseEventResponse {
        public String voter;

        public BigInteger candidateId;

        public String candidateName;
    }
}
