# time.thrift
namespace java com.cherkashyn.vitaliy.thrift.exchange
typedef i64 Timestamp
service TimeServer {
   Timestamp time()
}

