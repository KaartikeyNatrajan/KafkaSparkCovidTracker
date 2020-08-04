const React = require('react');
const ReactDOM = require('react-dom');
import { Line, Bar } from "react-chartjs-2";
//const client = require('./client');
import { chartExample1 } from "./chartdata.js";

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {cases: []};
	}

	componentDidMount() {
		fetch("/api/v1/query").then(response => response.json())
		.then((response) => {
			this.setState({cases: response.cases});
		});
	}

	render() {
		return (
			<div>
				<MyCoolChart chartData={this.state.cases}/>
			</div>
		)
	}
}

class MyCoolChart extends React.Component {
	render() {
		const cases = [];
		const labels = []
		for (var i = this.props.chartData.length - 1; i >= 0; i--) {
			cases.push(this.props.chartData[i].cases);
			labels.push(this.props.chartData[i].date);

		}

		const data1 = (canvas) => {
		    let ctx = canvas.getContext("2d");

		    let gradientStroke = ctx.createLinearGradient(0, 230, 0, 50);

		    gradientStroke.addColorStop(1, "rgba(29,140,248,0.2)");
		    gradientStroke.addColorStop(0.4, "rgba(29,140,248,0.0)");
		    gradientStroke.addColorStop(0, "rgba(29,140,248,0)"); //blue colors

		    return {
		      labels: labels,
		      datasets: [
		        {
		          label: "My First dataset",
		          fill: true,
		          backgroundColor: gradientStroke,
		          borderColor: "#1f8ef1",
		          borderWidth: 2,
		          borderDash: [],
		          borderDashOffset: 0.0,
		          pointBackgroundColor: "#1f8ef1",
		          pointBorderColor: "rgba(255,255,255,0)",
		          pointHoverBackgroundColor: "#1f8ef1",
		          pointBorderWidth: 20,
		          pointHoverRadius: 4,
		          pointHoverBorderWidth: 15,
		          pointRadius: 4,
		          data: cases
		        }
		      ]
		    };
		};
		// data.datasets.data = cases;
		// data.labels = labels;
		console.log(chartExample1);

		return (
			<Line
				data={data1}
				options={chartExample1.options}
			/>
		)
		
	}
}
ReactDOM.render(
	<App />,
	document.getElementById('react')
)