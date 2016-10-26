

/*

        Port port = LocalEV3.get().getPort("S1");
        RegulatedMotor rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        SensorModes colourSensor = new EV3ColorSensor(port);
        SampleProvider colourSampleProvider = colourSensor.getMode("Red");


        float[] sample = new float[colourSampleProvider.sampleSize()];

        int lval;
        int rval;

        while(true){
            colourSampleProvider.fetchSample(sample, 0);
            lval=1;
            rval=1;

            rMotor.setSpeed(200);
            lMotor.setSpeed(200);

            float k = 1;

            if(sample[0]>0.06){
                k = sample[0];
                if(k<0.15){
                    lval=Math.round(200*k);
                    lMotor.setSpeed(lval);
                }
                else{
                    rval = Math.round(200*k);
                    rMotor.setSpeed(rval);
                }
            }

            //lMotor.forward();
            //rMotor.forward();

            display(sample[0], lval, rval);
            Delay.msDelay(1000);
        }
    }
 */