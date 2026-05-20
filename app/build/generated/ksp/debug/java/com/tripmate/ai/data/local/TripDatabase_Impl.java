package com.tripmate.ai.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.tripmate.ai.data.local.dao.TripDao;
import com.tripmate.ai.data.local.dao.TripDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TripDatabase_Impl extends TripDatabase {
  private volatile TripDao _tripDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `trips` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `prompt` TEXT NOT NULL, `title` TEXT NOT NULL, `destination` TEXT NOT NULL, `budget` TEXT NOT NULL, `durationDays` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `itinerary_days` (`dayId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tripId` INTEGER NOT NULL, `dayNumber` INTEGER NOT NULL, `description` TEXT NOT NULL, FOREIGN KEY(`tripId`) REFERENCES `trips`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_itinerary_days_tripId` ON `itinerary_days` (`tripId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `places` (`placeId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dayId` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `timeHint` TEXT NOT NULL, `type` TEXT NOT NULL, FOREIGN KEY(`dayId`) REFERENCES `itinerary_days`(`dayId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_places_dayId` ON `places` (`dayId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '069fbdc2cf4c13742a7d8fd0afcaaddf')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `trips`");
        db.execSQL("DROP TABLE IF EXISTS `itinerary_days`");
        db.execSQL("DROP TABLE IF EXISTS `places`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTrips = new HashMap<String, TableInfo.Column>(8);
        _columnsTrips.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("prompt", new TableInfo.Column("prompt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("destination", new TableInfo.Column("destination", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("budget", new TableInfo.Column("budget", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("durationDays", new TableInfo.Column("durationDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrips = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrips = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrips = new TableInfo("trips", _columnsTrips, _foreignKeysTrips, _indicesTrips);
        final TableInfo _existingTrips = TableInfo.read(db, "trips");
        if (!_infoTrips.equals(_existingTrips)) {
          return new RoomOpenHelper.ValidationResult(false, "trips(com.tripmate.ai.data.local.entity.TripEntity).\n"
                  + " Expected:\n" + _infoTrips + "\n"
                  + " Found:\n" + _existingTrips);
        }
        final HashMap<String, TableInfo.Column> _columnsItineraryDays = new HashMap<String, TableInfo.Column>(4);
        _columnsItineraryDays.put("dayId", new TableInfo.Column("dayId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItineraryDays.put("tripId", new TableInfo.Column("tripId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItineraryDays.put("dayNumber", new TableInfo.Column("dayNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItineraryDays.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysItineraryDays = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysItineraryDays.add(new TableInfo.ForeignKey("trips", "CASCADE", "NO ACTION", Arrays.asList("tripId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesItineraryDays = new HashSet<TableInfo.Index>(1);
        _indicesItineraryDays.add(new TableInfo.Index("index_itinerary_days_tripId", false, Arrays.asList("tripId"), Arrays.asList("ASC")));
        final TableInfo _infoItineraryDays = new TableInfo("itinerary_days", _columnsItineraryDays, _foreignKeysItineraryDays, _indicesItineraryDays);
        final TableInfo _existingItineraryDays = TableInfo.read(db, "itinerary_days");
        if (!_infoItineraryDays.equals(_existingItineraryDays)) {
          return new RoomOpenHelper.ValidationResult(false, "itinerary_days(com.tripmate.ai.data.local.entity.ItineraryDayEntity).\n"
                  + " Expected:\n" + _infoItineraryDays + "\n"
                  + " Found:\n" + _existingItineraryDays);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaces = new HashMap<String, TableInfo.Column>(8);
        _columnsPlaces.put("placeId", new TableInfo.Column("placeId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("dayId", new TableInfo.Column("dayId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("timeHint", new TableInfo.Column("timeHint", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaces = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPlaces.add(new TableInfo.ForeignKey("itinerary_days", "CASCADE", "NO ACTION", Arrays.asList("dayId"), Arrays.asList("dayId")));
        final HashSet<TableInfo.Index> _indicesPlaces = new HashSet<TableInfo.Index>(1);
        _indicesPlaces.add(new TableInfo.Index("index_places_dayId", false, Arrays.asList("dayId"), Arrays.asList("ASC")));
        final TableInfo _infoPlaces = new TableInfo("places", _columnsPlaces, _foreignKeysPlaces, _indicesPlaces);
        final TableInfo _existingPlaces = TableInfo.read(db, "places");
        if (!_infoPlaces.equals(_existingPlaces)) {
          return new RoomOpenHelper.ValidationResult(false, "places(com.tripmate.ai.data.local.entity.PlaceEntity).\n"
                  + " Expected:\n" + _infoPlaces + "\n"
                  + " Found:\n" + _existingPlaces);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "069fbdc2cf4c13742a7d8fd0afcaaddf", "cde85c5a2d2f3287edb5b1a1bf947dd9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "trips","itinerary_days","places");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `trips`");
      _db.execSQL("DELETE FROM `itinerary_days`");
      _db.execSQL("DELETE FROM `places`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TripDao.class, TripDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TripDao tripDao() {
    if (_tripDao != null) {
      return _tripDao;
    } else {
      synchronized(this) {
        if(_tripDao == null) {
          _tripDao = new TripDao_Impl(this);
        }
        return _tripDao;
      }
    }
  }
}
