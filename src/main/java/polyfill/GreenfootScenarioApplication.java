package polyfill;

import greenfoot.core.Simulation;
import greenfoot.export.GreenfootScenarioViewer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// https://github.com/lfuelling/cleanfoot/blob/65b9cf51ea47949376cc3bf1c4553076e7231634/src/main/java/greenfoot/export/GreenfootScenarioApplication.java

public class GreenfootScenarioApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		GreenfootScenarioViewer greenfootScenarioViewer = new GreenfootScenarioViewer();
		Scene scene = new Scene(greenfootScenarioViewer);

		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setOnHiding(e -> {
			Simulation.getInstance().abort();

			// Fail safe: if we haven't exited after a second, force exit:
			Thread exiter = new Thread() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ignored) {
					}
					System.exit(1);
				}
			};
			// Don't let the exiter fallback prevent us exiting normally:
			exiter.setDaemon(true);
			exiter.start();
		});
	}
}
