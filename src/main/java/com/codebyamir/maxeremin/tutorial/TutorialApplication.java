package com.codebyamir.maxeremin.tutorial;

import com.codebyamir.maxeremin.tutorial.model.Station;
import com.codebyamir.maxeremin.tutorial.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;

@SpringBootApplication
@Slf4j
public class TutorialApplication implements CommandLineRunner{

	@Autowired
	private StationService stationService;

	public static void main(String[] args) {
		SpringApplication.run(TutorialApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... strings) throws Exception {

		/*final StopStation stopStationA = new StopStation();
        final StopStation stopStationB = new StopStation();
        final StopStation stopStationC = new StopStation();
        final StopStation stopStationD = new StopStation();*/

        Station stationA = new Station("Железнодорожный вокзал", 51.5408701, 46.0052467);
		Station stationB = new Station("ТЦ Лента", 51.5834314, 46.1033911);
		Station stationC = new Station("Энгельсский пляж", 51.502045, 46.1038253);
		Station stationD = new Station("ТК Мой Новый", 51.5071535, 45.9761751);

		stationA.getCloseStations().add(stationB);
		stationA.getCloseStations().add(stationC);
		stationA.getCloseStations().add(stationD);

		stationB.getCloseStations().add(stationA);
		stationB.getCloseStations().add(stationC);
		stationB.getCloseStations().add(stationD);

		stationC.getCloseStations().add(stationA);
		stationC.getCloseStations().add(stationB);
		stationC.getCloseStations().add(stationD);

		stationD.getCloseStations().add(stationA);
		stationD.getCloseStations().add(stationB);
		stationD.getCloseStations().add(stationC);

		stationService.saveStations(new LinkedHashSet<Station>(){{
			add(stationA);
			add(stationB);
			add(stationC);
			add(stationD);
		}});

		log.debug("Here is the list of stations: ");
		for (Station station :
				stationService.findAll()) {
			log.debug(station.getName());
		}

        /*stationService.saveStations(new LinkedHashSet<Station>(){{
			add(new Station("Железнодорожный вокзал", 51.5408701, 46.0052467, new LinkedHashSet<Station>(){{
				add(stationB);
				add(stationC);
				add(stationD);
			}}));

			add(new Station("ТЦ Лента", 51.5834314, 46.1033911, new LinkedHashSet<Station>(){{
				add(stationA);
				add(stationC);
				add(stationD);
			}}));

			add(new Station("Энгельсский пляж", 51.502045, 46.1038253, new LinkedHashSet<Station>(){{
				add(stationA);
				add(stationB);
				add(stationD);
			}}));

			add(new Station("ТК Мой Новый", 51.5071535, 45.9761751, new LinkedHashSet<Station>(){{
				add(stationA);
				add(stationC);
				add(stationB);
			}}));
		}});*/

		/*startStationService.saveStartStations(new LinkedHashSet<StartStation>(){{
			add(new StartStation("Железнодорожный вокзал", 51.5408701, 46.0052467, new LinkedHashSet<StopStation>(){{
				add(stopStationB);
				add(stopStationC);
				add(stopStationD);
			}}));

			add(new StartStation("ТЦ Лента", 51.5834314, 46.1033911, new LinkedHashSet<StopStation>(){{
				add(stopStationA);
				add(stopStationC);
				add(stopStationD);
			}}));

			add(new StartStation("Энгельсский пляж", 51.502045, 46.1038253, new LinkedHashSet<StopStation>(){{
				add(stopStationA);
				add(stopStationB);
				add(stopStationD);
			}}));

			add(new StartStation("ТК Мой Новый", 51.5071535, 45.9761751, new LinkedHashSet<StopStation>(){{
				add(stopStationA);
				add(stopStationC);
				add(stopStationB);
			}}));
		}});

		log.debug("Added the start station list: ");
		for (StartStation startStation :
				startStationService.findAll()) {
			log.debug(startStation.toString());
		}

		final StartStation startStationA = new StartStation();
		final StartStation startStationB = new StartStation();
		final StartStation startStationC = new StartStation();
		final StartStation startStationD = new StartStation();

        stopStationService.saveStopStations(new LinkedHashSet<StopStation>(){{
            add(new StopStation("Железнодорожный вокзал", 51.5408701, 46.0052467, new LinkedHashSet<StartStation>(){{
                add(startStationB);
                add(startStationC);
                add(startStationD);
            }}));

            add(new StopStation("ТЦ Лента", 51.5834314, 46.1033911, new LinkedHashSet<StartStation>(){{
                add(startStationA);
                add(startStationC);
                add(startStationD);
            }}));

            add(new StopStation("Энгельсский пляж", 51.502045, 46.1038253, new LinkedHashSet<StartStation>(){{
                add(startStationA);
                add(startStationB);
                add(startStationD);
            }}));

            add(new StopStation("ТК Мой Новый", 51.5071535, 45.9761751, new LinkedHashSet<StartStation>(){{
                add(startStationA);
                add(startStationC);
                add(startStationB);
            }}));
        }});

        log.debug("Added the stop station list: ");
        for (StopStation stopStation :
                stopStationService.findAll()) {
            log.debug(stopStation.toString());
        }*/
	}
}
