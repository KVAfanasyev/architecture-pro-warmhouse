package com.smarthome.devicemanagement.repository;

import com.smarthome.devicemanagement.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    Optional<Device> findBySerialNumber(String serialNumber);

    @Modifying
    @Query("UPDATE Device d SET d.status = 'OFFLINE' WHERE d.lastSeen < :threshold")
    void markOfflineDevices(LocalDateTime threshold);

    @Query("SELECT d FROM Device d WHERE d.id = :deviceId AND d.status = 'ONLINE'")
    Optional<Device> findOnlineDeviceById(String deviceId);
}