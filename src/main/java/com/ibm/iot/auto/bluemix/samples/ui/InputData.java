/**
 * Copyright 2016 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the �gLicense�h);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an �gAS IS�h BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.iot.auto.bluemix.samples.ui;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class InputData {
	
	private String inputFile;

	public InputData(String inputFile) {
		this.inputFile = inputFile;
	}

	public class CarProbe implements Comparable<CarProbe> {
		public String tripId;
		public String timestamp;
		public double heading;
		public double speed;
		public double longitude;
		public double latitude;
		
		public CarProbe(
				String tripId,
				String timestamp,
				double heading,
				double speed,
				double longitude,
				double latitude) {
			this.tripId				= tripId;
			this.timestamp			= timestamp;
			this.longitude			= longitude;
			this.latitude			= latitude;
			this.heading			= heading;
			this.speed				= speed;
		}
		
		@Override
		public int compareTo(CarProbe that) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Date thatDate = format.parse(that.timestamp, new ParsePosition(0));
			long thatDateLong = thatDate.getTime();
			Date thisDate = format.parse(this.timestamp, new ParsePosition(0));
			long thisDateLong = thisDate.getTime();
			Long diff = new Long(thisDateLong - thatDateLong);		
			return new Integer(diff.toString());
		}

		@Override
		public String toString() {
			return "tripId: "				+ tripId				+ ", " +
				"timepstamp: "			+ timestamp				+ ", " +
				"longitutude: "			+ longitude				+ ", " +
				"latitude: "			+ latitude				+ ", " +
				"heading: "				+ heading				+ ", " +
				"speend: "				+ speed					+ ", ";
		}
	}

	public List<CarProbe> getCarProbes() throws IOException, JSONException {
		List<CarProbe> carProbes = new ArrayList<CarProbe>();
		
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(inputFile);
			JSONTokener jsonTokener = new JSONTokener(fileReader);
			JSONArray cars = new JSONArray(jsonTokener);
			for (int i = 0; i < cars.length(); i++) {
				JSONObject car = cars.getJSONObject(i);
				CarProbe carProbe = new CarProbe(
						car.getString("trip_id"),
						car.getString("timestamp"),
						car.getDouble("heading"),
						car.getDouble("speed"),
						car.getDouble("longitude"),
						car.getDouble("latitude"));
				carProbes.add(carProbe);
			}
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
		}
		Collections.sort(carProbes);
		
		return carProbes;
	}
	
}
