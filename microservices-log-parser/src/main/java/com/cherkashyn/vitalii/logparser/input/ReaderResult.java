package com.cherkashyn.vitalii.logparser.input;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Set;

public class ReaderResult {
    private static final String DELIMITER = " ";
    private static final String DELIMITER_ROUTE = "->";
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Trace trace;
    private final ReadException traceException;

    ReaderResult(Trace trace){
        this.trace = trace;
        this.traceException = null;
    }

    ReaderResult(ReadException traceException){
        this.trace = null;
        this.traceException = traceException;
    }

    public boolean isTrace(){
        return this.trace!=null;
    }

    /**
     *
     * @param nextLine  "2013-10-23T10:12:36.458Z 2013-10-23T10:12:36.461Z ks2vhl7f service8 yxyyayym->nqipswiu";
     * @return result of parsing wrapped into {@link ReaderResult}:
     * <ul>
     *     <li> {@link Trace}</li>
     *     <li> {@link ReadException}</li>
     * </ul>
     */
    public static ReaderResult parse(String nextLine) {
        try{
            Trace trace = parseTrace(nextLine);
            Set<ConstraintViolation<Trace>> validationResult = validatorFactory.getValidator().validate(trace);
            if(validationResult.size()>0)
                throw new ReadException(validationResult);
            return new ReaderResult(trace);
        }catch(ReadException re){
            return new ReaderResult(re);
        }catch(DateTimeParseException pe){
            return new ReaderResult(new ReadException(pe.getMessage()));
        }

    }

    private static Trace parseTrace(String nextLine) {
        String[] elements = nextLine.split(DELIMITER);
        if(elements.length!=5)
            throw new ReadException("line is not valid, delimiters issue");
        Pair<String, String> route = parseRoute(elements[4]);
        return Trace.TraceBuilder.create()
                .setStart(parseDate(elements[0]))
                .setEnd(parseDate(elements[1]))
                .setId(elements[2])
                .setName(elements[3])
                .setSource(route.getLeft())
                .setDestination(route.getRight())
                .build();
    }

    /**
     *
     * @param element example "yxyyayym->nqipswiu"
     * @return source and destination points
     */
    private static Pair<String, String> parseRoute(String element) {
        String[] steps = element.split(ReaderResult.DELIMITER_ROUTE);
        if(steps.length!=2)
            throw new ReadException("source and destination point can't be recognized");
        return new ImmutablePair<>(steps[0], steps[1]);
    }

    private static Date parseDate(String value){
        return Date.from(Instant.parse(value));
    }

    public Trace getTrace() {
        return trace;
    }

    public ReadException getTraceException() {
        return traceException;
    }

    @Override
    public String toString() {
        return "ReaderResult{" +
                "trace=" + trace +
                ", traceException=" + traceException +
                '}';
    }
}
