const fs = require('fs');
const { Kafka } = require('kafkajs')

const kafka = new Kafka({
    clientId: 'my-app',
    brokers: ['0.0.0.0:9092']
})


/**
* Funtion puts data into kafka at regular intervals
*/
async function producer(){
    const producer = kafka.producer()
    await producer.connect()
    let rawdata = fs.readFileSync('data.json');
    let data = JSON.parse(rawdata);
    let records = data.records
    for (var i = 0; i < records.length; i++) {
        await producer.send({
            topic: 'cases-source1',
            messages: [{
                value: JSON.stringify(records[i])
            }],
        })
        
        // Wait 2 seconds before emitting the next record
        await sleep(2000);
    }
    await producer.disconnect()
}


function sleep(ms) {
    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
}


async function consumerFunc(){
    const consumer = kafka.consumer({ groupId: 'cases' })

    await consumer.connect()
    await consumer.subscribe({ topic: 'cases-source1', fromBeginning: true })

    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log({
                value: message.value.toString(),
            })
        },
    })    
}

producer();
// consumerFunc();