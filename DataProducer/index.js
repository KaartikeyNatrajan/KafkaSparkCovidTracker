const { Kafka } = require('kafkajs')
const fs = require('fs')

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ['0.0.0.0:9092']
})

async function producerFunc(){
    const producer = kafka.producer()

    await producer.connect()

    let indiaRaw = fs.readFileSync('india.json')
    let usaRaw = fs.readFileSync('usa.json')
    let usaRecords = JSON.parse(usaRaw)
    

    let indiaData = JSON.parse(indiaRaw)    
    let indiaRecords = indiaData.cases_time_series

    len = Math.min(indiaRecords.length,usaRecords.length)

    for (let i = 0; i <= len; i++) {
        const usaElement = usaRecords[i];
        const indiaElement = indiaRecords[i];
        await producer.send({
            topic: 'usa-topic',
            messages: [
                { value: JSON.stringify(usaElement) }
            ],
            })        
        
        await producer.send({
            topic: 'india-topic',
            messages: [
                { value : JSON.stringify(indiaElement) }
            ],
        })

        await sleep(3000)        

    }
    await producer.disconnect()

}

function sleep(ms) {
    return new Promise((resolve) => {
      setTimeout(resolve, ms);
    });
} 

async function consumerFunc(){
    const consumer = kafka.consumer({ groupId: 'test-group' })

    await consumer.connect()
    await consumer.subscribe({ topic: 'usa-topic', fromBeginning: true })

    await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
        console.log({
        value: message.value.toString(),
        })
    },
    })        
}

producerFunc()
// consumerFunc()