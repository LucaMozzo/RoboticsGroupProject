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

<h3>Values</h3>
<ul>
<li>dval = 200</li>
<li> float k = 320; </li>
<li> double e= float sample and if (e &lt 0.3 || e &gt 0.45) then e-=0.42; </li> 
<li> float kprop = 1.3; </li>
</ul>

    
<h3>Colour values</h3>
<table style="width:100%">
<tr>
<th>Percent off</th>
<th><strike>Red Value (2 spacers)</strike></th>
<th>Red Value (1 spacer)</th>
<th><strike>RGB Value (2 spacers)</strike></th>
<th><strike>RGB Value (1 spacer)</strike></th>
</tr>
<tr><td>0% (BLACK)</td><td>&lt0.05</td><td>&lt0.08-0.1</td><td>0.02</td><td>0.03</td></tr>
<tr><td>25%</td><td>0.1</td><td>0.09-0.13</td><td>0.04</td><td>0.07</td></tr>
<tr><td>50%</td><td>0.35</td><td>0.38-0.4</td><td>0.05-0.06</td><td>0.17</td></tr>
<tr><td>75%</td><td>0.45</td><td>0.65-0.8</td><td>0.18</td><td>0.27</td></tr>
<tr><td>100% (WHITE)</td><td>&gt0.49</td><td>&gt0.8</td><td>&gt0.24</td><td>&gt0.34</td></tr>
</table> 

<h3>Working configuration</h3>
<ul>
<li>PID config</li>
<li>Ksym = 1.4 (1.3)</li>
<li>Kp = 320</li>
<li>Kd = 100</li>
<li>Ki = 3 (4 more stable)</li>
<ul>
