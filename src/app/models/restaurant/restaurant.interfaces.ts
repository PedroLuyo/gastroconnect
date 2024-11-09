export interface Restaurant {
  identificador: number;
  uid: string; // Agregamos el uid
  nombre: string;
  informacionNegocio: {
    ruc: number;
    razonSocial: string;
    tipoNegocio: string;
    categoria: string[];
  };
  media: {
    logo: string;
    fondo: string;
  };
  contacto: {
    telefono: number;
    email: string;
    sitioWeb: string;
    redesSociales: {
      facebook: string;
      instagram: string;
      x: string;
      youtube: string;
    };
  };
  ubicacion: {
    direccion: string;
    distrito: string;
    ciudad: string;
    pais: string;
    referencias: string;
  };
  horario: {
    lunes: DaySchedule;
    martes: DaySchedule;
    miercoles: DaySchedule;
    jueves: DaySchedule;
    viernes: DaySchedule;
    sabado: DaySchedule;
    domingo: DaySchedule;
    feriados: DaySchedule;
  };
  caracteristicas: {
    aforo: number;
    estacionamiento: boolean;
    wifi: boolean;
    reservas: boolean;
    delivery: boolean;
    recojo: boolean;
    menu: boolean;
    carta: boolean;
  };
  metodosPago: string[];
  estado: boolean;
}

interface DaySchedule {
abierto: boolean;
turnos: TimeRange[];
}

interface TimeRange {
apertura: string;
cierre: string;
}