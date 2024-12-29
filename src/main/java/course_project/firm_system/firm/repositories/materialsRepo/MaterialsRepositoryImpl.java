package course_project.firm_system.firm.repositories.materialsRepo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.factories.FactoryMaterials;
import course_project.firm_system.firm.models.operations.OpMaterials;
import course_project.firm_system.firm.models.reports.MaterialsAccounting;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public class MaterialsRepositoryImpl implements MaterialsRepository {


  private final ObjectMapper objectMapper;

  private static final File materialAccountingsFilePath = new File( "src/main/resources/db/reports/materialAccountings.json");
  private static final File materialFilePath = new File("src/main/resources/db/materials.json");
  private static final File factoryMaterialsFilePath = new File("src/main/resources/db/factory/fctrMaterials.json");
  private static final File opMaterialsFilePath = new File( "src/main/resources/db/op/opMaterials.json");

  public MaterialsRepositoryImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public List<MaterialsAccounting> getMaterialAccountings() throws IOException {

    if (materialAccountingsFilePath.exists()) {
      return objectMapper.readValue(materialAccountingsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }


  @Override
  public List<FactoryMaterials> getFactoryMaterials() throws IOException {
    if (factoryMaterialsFilePath.exists()) {
      return objectMapper.readValue(factoryMaterialsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<OpMaterials> getOpMaterials() throws IOException {

    if (opMaterialsFilePath.exists()) {
      return objectMapper.readValue(opMaterialsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<Material> getAllMaterials() throws IOException {

    if (materialFilePath.exists()) {
      return objectMapper.readValue(materialFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public void saveFactoryMaterials(List<FactoryMaterials> list) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(factoryMaterialsFilePath, list);
  }

  @Override
  public void saveMaterialAccounting(List<MaterialsAccounting> list) throws IOException {

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(materialAccountingsFilePath, list);

  }



}
