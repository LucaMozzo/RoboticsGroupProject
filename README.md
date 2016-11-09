# RoboticsGroupProject
<h3>Uploading the JAR file to the EV3</h3>
Following the tutorial [here](http://www.rapidpm.org/2013/12/27/developing-lejos-programs-with-intelli.html).

Download the [tool](http://www.rapidpm.org/2013/12/27/developing-lejos-programs-with-intelli.html) and Run the following commands in the cmd <br><code>pscp -scp HELLOWORLD\out\artifacts\HelloWorld_jar\HelloWorld.jar </code><br/><code>root@IP_OF_EV3:/home/lejos/programs</code>

<h3>Ports</h3>
<ul>
<li>RightMotor <b>A</b>: <code>rMotor</code></li>
<li>LeftMotor <b>B</b>: <code>lMotor</code></li>
<li>UltraSonicMotor <b>C</b>: <code>sMotor</code></li>
</ul>
<ul>
<li>Light Sensor <b>S1</b>: <code>lSensor</code></li>
<li>Ultrasonic <b>S2</b>: <code>uSensor</code></li>
</ul>

<h3>Colour values</h3>
<table style="width:100%">
<tr>
<th>Percent out</th>
<th>Value</th>
</tr>
<tr><td>0% (BLACK)</td><td>&lt0.06</td></tr>
<tr><td>25%</td><td>0.08-0.1</td></tr>
<tr><td>50%</td><td>0.18-0.2</td></tr>
<tr><td>75%</td><td>0.35-0.43</td></tr>
<tr><td>100% (WHITE)</td><td>&gt0.5</td></tr>
</table> 
