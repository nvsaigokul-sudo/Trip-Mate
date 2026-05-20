package com.tripmate.ai.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tripmate.ai.data.local.entity.ItineraryDayEntity;
import com.tripmate.ai.data.local.entity.PlaceEntity;
import com.tripmate.ai.data.local.entity.TripEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TripDao_Impl implements TripDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TripEntity> __insertionAdapterOfTripEntity;

  private final EntityInsertionAdapter<ItineraryDayEntity> __insertionAdapterOfItineraryDayEntity;

  private final EntityInsertionAdapter<PlaceEntity> __insertionAdapterOfPlaceEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTripFavoriteStatus;

  public TripDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTripEntity = new EntityInsertionAdapter<TripEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trips` (`id`,`prompt`,`title`,`destination`,`budget`,`durationDays`,`createdAt`,`isFavorite`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TripEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPrompt());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDestination());
        statement.bindString(5, entity.getBudget());
        statement.bindLong(6, entity.getDurationDays());
        statement.bindLong(7, entity.getCreatedAt());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__insertionAdapterOfItineraryDayEntity = new EntityInsertionAdapter<ItineraryDayEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `itinerary_days` (`dayId`,`tripId`,`dayNumber`,`description`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ItineraryDayEntity entity) {
        statement.bindLong(1, entity.getDayId());
        statement.bindLong(2, entity.getTripId());
        statement.bindLong(3, entity.getDayNumber());
        statement.bindString(4, entity.getDescription());
      }
    };
    this.__insertionAdapterOfPlaceEntity = new EntityInsertionAdapter<PlaceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `places` (`placeId`,`dayId`,`name`,`description`,`latitude`,`longitude`,`timeHint`,`type`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaceEntity entity) {
        statement.bindLong(1, entity.getPlaceId());
        statement.bindLong(2, entity.getDayId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDescription());
        statement.bindDouble(5, entity.getLatitude());
        statement.bindDouble(6, entity.getLongitude());
        statement.bindString(7, entity.getTimeHint());
        statement.bindString(8, entity.getType());
      }
    };
    this.__preparedStmtOfUpdateTripFavoriteStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE trips SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertTrip(final TripEntity trip, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTripEntity.insertAndReturnId(trip);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertDay(final ItineraryDayEntity day,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfItineraryDayEntity.insertAndReturnId(day);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPlaces(final List<PlaceEntity> places,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlaceEntity.insert(places);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTripFavoriteStatus(final long tripId, final boolean isFavorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTripFavoriteStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, tripId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateTripFavoriteStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TripEntity>> getAllTrips() {
    final String _sql = "SELECT * FROM trips ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trips"}, new Callable<List<TripEntity>>() {
      @Override
      @NonNull
      public List<TripEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPrompt = CursorUtil.getColumnIndexOrThrow(_cursor, "prompt");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDestination = CursorUtil.getColumnIndexOrThrow(_cursor, "destination");
          final int _cursorIndexOfBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "budget");
          final int _cursorIndexOfDurationDays = CursorUtil.getColumnIndexOrThrow(_cursor, "durationDays");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<TripEntity> _result = new ArrayList<TripEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TripEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPrompt;
            _tmpPrompt = _cursor.getString(_cursorIndexOfPrompt);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDestination;
            _tmpDestination = _cursor.getString(_cursorIndexOfDestination);
            final String _tmpBudget;
            _tmpBudget = _cursor.getString(_cursorIndexOfBudget);
            final int _tmpDurationDays;
            _tmpDurationDays = _cursor.getInt(_cursorIndexOfDurationDays);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new TripEntity(_tmpId,_tmpPrompt,_tmpTitle,_tmpDestination,_tmpBudget,_tmpDurationDays,_tmpCreatedAt,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTripById(final long tripId, final Continuation<? super TripEntity> $completion) {
    final String _sql = "SELECT * FROM trips WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tripId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TripEntity>() {
      @Override
      @Nullable
      public TripEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPrompt = CursorUtil.getColumnIndexOrThrow(_cursor, "prompt");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDestination = CursorUtil.getColumnIndexOrThrow(_cursor, "destination");
          final int _cursorIndexOfBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "budget");
          final int _cursorIndexOfDurationDays = CursorUtil.getColumnIndexOrThrow(_cursor, "durationDays");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final TripEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPrompt;
            _tmpPrompt = _cursor.getString(_cursorIndexOfPrompt);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDestination;
            _tmpDestination = _cursor.getString(_cursorIndexOfDestination);
            final String _tmpBudget;
            _tmpBudget = _cursor.getString(_cursorIndexOfBudget);
            final int _tmpDurationDays;
            _tmpDurationDays = _cursor.getInt(_cursorIndexOfDurationDays);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _result = new TripEntity(_tmpId,_tmpPrompt,_tmpTitle,_tmpDestination,_tmpBudget,_tmpDurationDays,_tmpCreatedAt,_tmpIsFavorite);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getDaysForTrip(final long tripId,
      final Continuation<? super List<ItineraryDayEntity>> $completion) {
    final String _sql = "SELECT * FROM itinerary_days WHERE tripId = ? ORDER BY dayNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tripId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ItineraryDayEntity>>() {
      @Override
      @NonNull
      public List<ItineraryDayEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "dayId");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfDayNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "dayNumber");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final List<ItineraryDayEntity> _result = new ArrayList<ItineraryDayEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItineraryDayEntity _item;
            final long _tmpDayId;
            _tmpDayId = _cursor.getLong(_cursorIndexOfDayId);
            final long _tmpTripId;
            _tmpTripId = _cursor.getLong(_cursorIndexOfTripId);
            final int _tmpDayNumber;
            _tmpDayNumber = _cursor.getInt(_cursorIndexOfDayNumber);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item = new ItineraryDayEntity(_tmpDayId,_tmpTripId,_tmpDayNumber,_tmpDescription);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlacesForDay(final long dayId,
      final Continuation<? super List<PlaceEntity>> $completion) {
    final String _sql = "SELECT * FROM places WHERE dayId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, dayId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaceEntity>>() {
      @Override
      @NonNull
      public List<PlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "placeId");
          final int _cursorIndexOfDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "dayId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTimeHint = CursorUtil.getColumnIndexOrThrow(_cursor, "timeHint");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<PlaceEntity> _result = new ArrayList<PlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaceEntity _item;
            final long _tmpPlaceId;
            _tmpPlaceId = _cursor.getLong(_cursorIndexOfPlaceId);
            final long _tmpDayId;
            _tmpDayId = _cursor.getLong(_cursorIndexOfDayId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpTimeHint;
            _tmpTimeHint = _cursor.getString(_cursorIndexOfTimeHint);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new PlaceEntity(_tmpPlaceId,_tmpDayId,_tmpName,_tmpDescription,_tmpLatitude,_tmpLongitude,_tmpTimeHint,_tmpType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
