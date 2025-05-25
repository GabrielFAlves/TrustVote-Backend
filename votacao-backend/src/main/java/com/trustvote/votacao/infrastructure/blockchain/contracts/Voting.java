package com.trustvote.votacao.infrastructure.blockchain.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
import org.web3j.tuples.generated.Tuple3;
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
    public static final String BINARY = "0x608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610dfe806100326000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c806335b8e8201161005b57806335b8e820146100e65780636fac312814610108578063a63858031461012b578063f851a4401461013e57600080fd5b806309c863671461008d57806326541b56146100a25780632d35a8a2146100b55780632e6997fe146100d1575b600080fd5b6100a061009b366004610896565b610169565b005b6100a06100b03660046108dd565b6101de565b6100be60035481565b6040519081526020015b60405180910390f35b6100d9610315565b6040516100c89190610987565b6100f96100f4366004610a18565b610515565b6040516100c893929190610a31565b61011b610116366004610a67565b61067c565b60405190151581526020016100c8565b6100a0610139366004610aa4565b6106a7565b600054610151906001600160a01b031681565b6040516001600160a01b0390911681526020016100c8565b6000546001600160a01b0316331461019c5760405162461bcd60e51b815260040161019390610ae9565b60405180910390fd5b60035482106101bd5760405162461bcd60e51b815260040161019390610b2a565b6000828152600160208190526040909120016101d98282610bd8565b505050565b6000546001600160a01b031633146102085760405162461bcd60e51b815260040161019390610ae9565b60008251116102655760405162461bcd60e51b8152602060048201526024808201527f4e6f6d6520646f2063616e64696461746f206e616f20706f6465207365722076604482015263617a696f60e01b6064820152608401610193565b604080516060810182528381526020808201849052600082840181905260035481526001909152919091208151819061029e9082610bd8565b50602082015160018201906102b39082610bd8565b5060409182015160029091015560035490517fe64f834082661dd9ed89427b87edb6270fd6dad17198b0938f0edd058914c87f916102f49185908590610c98565b60405180910390a16003805490600061030c83610ccd565b91905055505050565b6060600060035467ffffffffffffffff811115610334576103346107f3565b60405190808252806020026020018201604052801561038957816020015b61037660405180606001604052806060815260200160608152602001600081525090565b8152602001906001900390816103525790505b50905060005b60035481101561050f57600081815260016020526040908190208151606081019092528054829082906103c190610b56565b80601f01602080910402602001604051908101604052809291908181526020018280546103ed90610b56565b801561043a5780601f1061040f5761010080835404028352916020019161043a565b820191906000526020600020905b81548152906001019060200180831161041d57829003601f168201915b5050505050815260200160018201805461045390610b56565b80601f016020809104026020016040519081016040528092919081815260200182805461047f90610b56565b80156104cc5780601f106104a1576101008083540402835291602001916104cc565b820191906000526020600020905b8154815290600101906020018083116104af57829003601f168201915b505050505081526020016002820154815250508282815181106104f1576104f1610cf4565b6020026020010181905250808061050790610ccd565b91505061038f565b50919050565b6060806000600354841061053b5760405162461bcd60e51b815260040161019390610b2a565b6000848152600160208190526040909120600281015481549192830191839061056390610b56565b80601f016020809104026020016040519081016040528092919081815260200182805461058f90610b56565b80156105dc5780601f106105b1576101008083540402835291602001916105dc565b820191906000526020600020905b8154815290600101906020018083116105bf57829003601f168201915b505050505092508180546105ef90610b56565b80601f016020809104026020016040519081016040528092919081815260200182805461061b90610b56565b80156106685780601f1061063d57610100808354040283529160200191610668565b820191906000526020600020905b81548152906001019060200180831161064b57829003601f168201915b505050505091509250925092509193909250565b600060028260405161068e9190610d0a565b9081526040519081900360200190205460ff1692915050565b6000546001600160a01b031633146106d15760405162461bcd60e51b815260040161019390610ae9565b6002826040516106e19190610d0a565b9081526040519081900360200190205460ff16156107355760405162461bcd60e51b81526020600482015260116024820152704573746520435046206a6120766f746f7560781b6044820152606401610193565b60035481106107565760405162461bcd60e51b815260040161019390610b2a565b600081815260016020526040812060020180549161077383610ccd565b9190505550600160028360405161078a9190610d0a565b9081526040805160209281900383018120805460ff1916941515949094179093556000848152600190925290207f5880228a25ad64ec52db0b3b00f6e7f108477607f09b869879c4cacbf0ce2518916107e7918591859190610d26565b60405180910390a15050565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261081a57600080fd5b813567ffffffffffffffff80821115610835576108356107f3565b604051601f8301601f19908116603f0116810190828211818310171561085d5761085d6107f3565b8160405283815286602085880101111561087657600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080604083850312156108a957600080fd5b82359150602083013567ffffffffffffffff8111156108c757600080fd5b6108d385828601610809565b9150509250929050565b600080604083850312156108f057600080fd5b823567ffffffffffffffff8082111561090857600080fd5b61091486838701610809565b9350602085013591508082111561092a57600080fd5b506108d385828601610809565b60005b8381101561095257818101518382015260200161093a565b50506000910152565b60008151808452610973816020860160208601610937565b601f01601f19169290920160200192915050565b60006020808301818452808551808352604092508286019150828160051b87010184880160005b83811015610a0a57603f198984030185528151606081518186526109d48287018261095b565b915050888201518582038a8701526109ec828261095b565b928901519589019590955250948701949250908601906001016109ae565b509098975050505050505050565b600060208284031215610a2a57600080fd5b5035919050565b606081526000610a44606083018661095b565b8281036020840152610a56818661095b565b915050826040830152949350505050565b600060208284031215610a7957600080fd5b813567ffffffffffffffff811115610a9057600080fd5b610a9c84828501610809565b949350505050565b60008060408385031215610ab757600080fd5b823567ffffffffffffffff811115610ace57600080fd5b610ada85828601610809565b95602094909401359450505050565b60208082526021908201527f4170656e6173206f2061646d696e20706f6465206578656375746172206973736040820152606f60f81b606082015260800190565b60208082526012908201527143616e64696461746f20696e76616c69646f60701b604082015260600190565b600181811c90821680610b6a57607f821691505b60208210810361050f57634e487b7160e01b600052602260045260246000fd5b601f8211156101d957600081815260208120601f850160051c81016020861015610bb15750805b601f850160051c820191505b81811015610bd057828155600101610bbd565b505050505050565b815167ffffffffffffffff811115610bf257610bf26107f3565b610c0681610c008454610b56565b84610b8a565b602080601f831160018114610c3b5760008415610c235750858301515b600019600386901b1c1916600185901b178555610bd0565b600085815260208120601f198616915b82811015610c6a57888601518255948401946001909101908401610c4b565b5085821015610c885787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b838152606060208201526000610cb1606083018561095b565b8281036040840152610cc3818561095b565b9695505050505050565b600060018201610ced57634e487b7160e01b600052601160045260246000fd5b5060010190565b634e487b7160e01b600052603260045260246000fd5b60008251610d1c818460208701610937565b9190910192915050565b606081526000610d39606083018661095b565b60208581850152838203604085015260008554610d5581610b56565b80855260018281168015610d705760018114610d8a57610db8565b60ff1984168787015282151560051b870186019450610db8565b896000528560002060005b84811015610db0578154898201890152908301908701610d95565b880187019550505b50929a995050505050505050505056fea26469706673582212203fd276c57f90b730ed7de03ff11fd2f77c6ba17067f2b4895278a7589458ee8f64736f6c63430008140033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDCANDIDATE = "addCandidate";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_CANDIDATESCOUNT = "candidatesCount";

    public static final String FUNC_GETALLCANDIDATES = "getAllCandidates";

    public static final String FUNC_GETCANDIDATE = "getCandidate";

    public static final String FUNC_HASCPFVOTED = "hasCpfVoted";

    public static final String FUNC_UPDATECANDIDATEPHOTO = "updateCandidatePhoto";

    public static final String FUNC_VOTE = "vote";

    public static final Event CANDIDATEADDED_EVENT = new Event("CandidateAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event VOTED_EVENT = new Event("Voted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
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
            typedResponse.photoUrl = (String) eventValues.getNonIndexedValues().get(2).getValue();
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
        typedResponse.photoUrl = (String) eventValues.getNonIndexedValues().get(2).getValue();
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
            typedResponse.cpf = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.candidateId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.candidateName = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotedEventResponse getVotedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTED_EVENT, log);
        VotedEventResponse typedResponse = new VotedEventResponse();
        typedResponse.log = log;
        typedResponse.cpf = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.candidateId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.candidateName = (String) eventValues.getNonIndexedValues().get(2).getValue();
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

    public RemoteFunctionCall<TransactionReceipt> addCandidate(String _name, String _photoUrl) {
        final Function function = new Function(
                FUNC_ADDCANDIDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_photoUrl)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN, 
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

    public RemoteFunctionCall<Tuple3<String, String, BigInteger>> getCandidate(
            BigInteger _candidateId) {
        final Function function = new Function(FUNC_GETCANDIDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_candidateId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, BigInteger>>(function,
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> hasCpfVoted(String cpf) {
        final Function function = new Function(FUNC_HASCPFVOTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cpf)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCandidatePhoto(BigInteger _candidateId,
            String _photoUrl) {
        final Function function = new Function(
                FUNC_UPDATECANDIDATEPHOTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_candidateId), 
                new org.web3j.abi.datatypes.Utf8String(_photoUrl)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> vote(String cpf, BigInteger _candidateId) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cpf), 
                new org.web3j.abi.datatypes.generated.Uint256(_candidateId)), 
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
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Voting.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<Voting> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Voting.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Voting> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Voting.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Voting> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Voting.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

        public String photoUrl;

        public BigInteger voteCount;

        public Candidate(String name, String photoUrl, BigInteger voteCount) {
            super(new org.web3j.abi.datatypes.Utf8String(name), 
                    new org.web3j.abi.datatypes.Utf8String(photoUrl), 
                    new org.web3j.abi.datatypes.generated.Uint256(voteCount));
            this.name = name;
            this.photoUrl = photoUrl;
            this.voteCount = voteCount;
        }

        public Candidate(Utf8String name, Utf8String photoUrl, Uint256 voteCount) {
            super(name, photoUrl, voteCount);
            this.name = name.getValue();
            this.photoUrl = photoUrl.getValue();
            this.voteCount = voteCount.getValue();
        }
    }

    public static class CandidateAddedEventResponse extends BaseEventResponse {
        public BigInteger candidateId;

        public String name;

        public String photoUrl;
    }

    public static class VotedEventResponse extends BaseEventResponse {
        public String cpf;

        public BigInteger candidateId;

        public String candidateName;
    }
}
