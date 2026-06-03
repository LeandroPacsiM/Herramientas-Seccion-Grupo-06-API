package com.llamatours.common;

import com.llamatours.enums.Difficulty;
import com.llamatours.enums.Role;
import com.llamatours.user.entity.User;
import com.llamatours.user.repository.UserRepository;
import com.llamatours.expedition.entity.Availability;
import com.llamatours.expedition.entity.Expedition;
import com.llamatours.expedition.entity.Image;
import com.llamatours.expedition.entity.Itinerary;
import com.llamatours.expedition.repository.ExpeditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ExpeditionRepository expeditionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        seedUsers();
        seedExpeditions();
    }

    private void seedUsers() {
        userRepository.save(User.builder()
                .name("Admin LlamaTours")
                .email("admin@llamatours.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build());

        userRepository.save(User.builder()
                .name("Juan Pérez")
                .email("user@llamatours.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.USER)
                .build());
    }

    private void seedExpeditions() {
        expeditionRepository.saveAll(List.of(
                createCaminoInca(),
                createMontana7Colores(),
                createAusangate(),
                createSalkantay(),
                createHuayhuash(),
                createLaguna69(),
                createChoquequirao(),
                createPastoruri(),
                createLares(),
                createSantaCruz()
        ));
    }

    private Expedition createCaminoInca() {
        Expedition exp = Expedition.builder()
                .name("Camino Inca Clásico")
                .slug("camino-inca-clasico")
                .description("El trek más famoso de Sudamérica. Cuatro días de caminata atravesando paisajes andinos, ruinas incas y bosques nubosos hasta llegar a la majestuosa ciudadela de Machu Picchu.")
                .price(599.99)
                .durationDays(4)
                .difficulty(Difficulty.MODERATE)
                .location("Cusco, Perú")
                .build();

        exp.getItineraries().addAll(List.of(
                itinerary(exp, 1, "Inicio del Camino", "Salida desde Cusco hacia el km 82. Caminata de 12 km hasta Wayllabamba."),
                itinerary(exp, 2, "Paso de la Mujer Muerta", "Ascenso al punto más alto (4,215 msnm)."),
                itinerary(exp, 3, "Ruinas y Bosque Nuboso", "Visita a Runkurakay, Sayacmarca y Phuyupatamarca."),
                itinerary(exp, 4, "Machu Picchu", "Salida temprano hacia Inti Punku. Primera vista de Machu Picchu.")
        ));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1532996152552-eaffc4edfc1a?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1543385426-191664295b58?w=1080", 2),
                image(exp, "https://images.unsplash.com/photo-1509216242873-7786f446f465?w=1080", 3)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 5), 20, 15),
                availability(exp, LocalDate.of(2026, 7, 15), LocalDate.of(2026, 7, 19), 20, 8),
                availability(exp, LocalDate.of(2026, 8, 10), LocalDate.of(2026, 8, 14), 20, 20)
        ));
        return exp;
    }

    private Expedition createMontana7Colores() {
        Expedition exp = Expedition.builder()
                .name("Montaña de 7 Colores")
                .slug("montana-7-colores")
                .description("Vinicunca, la montaña arcoíris más famosa del mundo. Trek de un día a 5,200 msnm.")
                .price(89.99)
                .durationDays(1)
                .difficulty(Difficulty.MODERATE)
                .location("Cusco, Perú")
                .build();

        exp.getItineraries().add(itinerary(exp, 1, "Ascenso a la Montaña Arcoíris", "Salida 4 AM desde Cusco. Caminata de 2-3 horas hasta la cumbre."));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1631231248242-f82e0a0e2ea6?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1744295816404-4c73fd69e0f2?w=1080", 2),
                image(exp, "https://images.unsplash.com/photo-1645740713640-ea2b2025dcba?w=1080", 3)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 5), LocalDate.of(2026, 6, 5), 30, 22),
                availability(exp, LocalDate.of(2026, 6, 12), LocalDate.of(2026, 6, 12), 30, 30),
                availability(exp, LocalDate.of(2026, 7, 20), LocalDate.of(2026, 7, 20), 30, 5)
        ));
        return exp;
    }

    private Expedition createAusangate() {
        Expedition exp = Expedition.builder()
                .name("Ausangate Trek")
                .slug("ausangate-trek")
                .description("Expedición mística alrededor del Apu Ausangate (6,384 msnm). Siete días de trekking.")
                .price(945.00)
                .durationDays(7)
                .difficulty(Difficulty.HARD)
                .location("Cusco, Perú")
                .build();

        exp.getItineraries().addAll(List.of(
                itinerary(exp, 1, "Tinki - Upis", "Transfer a Tinki. Inicio del trek hacia Upis."),
                itinerary(exp, 2, "Upis - Arapa Pass", "Ascenso al paso Arapa (4,850 msnm)."),
                itinerary(exp, 3, "Paso Palomani", "Cruce del paso Palomani (5,200 msnm)."),
                itinerary(exp, 4, "Laguna Sibinacocha", "Caminata hacia la laguna más grande de la región."),
                itinerary(exp, 5, "Jampa Pass", "Cruce del paso Jampa (5,100 msnm)."),
                itinerary(exp, 6, "Pacchanta", "Descanso en aguas termales naturales."),
                itinerary(exp, 7, "Retorno a Cusco", "Transfer de regreso a Cusco.")
        ));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1551632811-561732d1e306?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1582161095006-7c4edf743cd9?w=1080", 2),
                image(exp, "https://images.unsplash.com/photo-1637580981046-6e14709be202?w=1080", 3)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 10), LocalDate.of(2026, 6, 17), 12, 7),
                availability(exp, LocalDate.of(2026, 7, 22), LocalDate.of(2026, 7, 29), 12, 12)
        ));
        return exp;
    }

    private Expedition createSalkantay() {
        Expedition exp = Expedition.builder()
                .name("Salkantay Trek")
                .slug("salkantay-trek")
                .description("Ruta alternativa a Machu Picchu atravesando el majestuoso nevado Salkantay (6,271 msnm).")
                .price(475.00)
                .durationDays(5)
                .difficulty(Difficulty.MODERATE)
                .location("Cusco, Perú")
                .build();

        exp.getItineraries().addAll(List.of(
                itinerary(exp, 1, "Soraypampa", "Transfer a Mollepata. Caminata hasta Soraypampa."),
                itinerary(exp, 2, "Paso Salkantay", "Ascenso al paso Salkantay (4,650 msnm)."),
                itinerary(exp, 3, "Selva Alta", "Cambio de ecosistema hacia la selva alta."),
                itinerary(exp, 4, "Aguas Calientes", "Caminata por vías del tren."),
                itinerary(exp, 5, "Machu Picchu", "Visita guiada a Machu Picchu.")
        ));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1614586125858-e695dd97d1b6?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1598859409659-b88fc15bbc2f?w=1080", 2)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 8), LocalDate.of(2026, 6, 13), 16, 10),
                availability(exp, LocalDate.of(2026, 7, 5), LocalDate.of(2026, 7, 10), 16, 16)
        ));
        return exp;
    }

    private Expedition createHuayhuash() {
        Expedition exp = Expedition.builder()
                .name("Huayhuash Circuit")
                .slug("huayhuash-circuit")
                .description("El circuito de trekking más espectacular de Perú. Diez días rodeando la Cordillera Huayhuash.")
                .price(1250.00)
                .durationDays(10)
                .difficulty(Difficulty.HARD)
                .location("Huaraz, Perú")
                .build();

        exp.getItineraries().addAll(List.of(
                itinerary(exp, 1, "Llamac - Cuartelhuain", "Inicio del circuito desde Llamac."),
                itinerary(exp, 2, "Paso Cacananpunta", "Cruce del primer paso a 4,700 msnm."),
                itinerary(exp, 3, "Laguna Mitucocha", "Caminata hacia la laguna Mitucocha."),
                itinerary(exp, 4, "Paso Carhuac", "Ascenso al paso Carhuac (4,650 msnm)."),
                itinerary(exp, 5, "Huayhuash", "Campamento base del nevado Huayhuash."),
                itinerary(exp, 6, "Paso San Antonio", "Cruce del paso San Antonio (5,000 msnm)."),
                itinerary(exp, 7, "Laguna Jahuacocha", "Trekking hasta la laguna más hermosa."),
                itinerary(exp, 8, "Paso Tapush", "Cruce del paso Tapush (4,750 msnm)."),
                itinerary(exp, 9, "Cajatambo", "Descenso final hacia Cajatambo."),
                itinerary(exp, 10, "Retorno a Huaraz", "Transfer de regreso a Huaraz.")
        ));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1551632811-561732d1e306?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1582161095006-7c4edf743cd9?w=1080", 2)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 15), LocalDate.of(2026, 6, 25), 10, 4),
                availability(exp, LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 11), 10, 10)
        ));
        return exp;
    }

    private Expedition createLaguna69() {
        Expedition exp = Expedition.builder()
                .name("Laguna 69")
                .slug("laguna-69")
                .description("Trek de un día a una de las lagunas más hermosas de la Cordillera Blanca.")
                .price(65.00)
                .durationDays(1)
                .difficulty(Difficulty.MODERATE)
                .location("Huaraz, Perú")
                .build();

        exp.getItineraries().add(itinerary(exp, 1, "Laguna 69", "Salida desde Huaraz hacia Cebollapampa. Caminata de 3 horas hasta Laguna 69."));
        exp.getImages().add(image(exp, "https://bushop.com/peru/wp-content/uploads/sites/10/laguna69-featured-1_1.jpg", 1));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 6), LocalDate.of(2026, 6, 6), 25, 18),
                availability(exp, LocalDate.of(2026, 6, 20), LocalDate.of(2026, 6, 20), 25, 25),
                availability(exp, LocalDate.of(2026, 7, 10), LocalDate.of(2026, 7, 10), 25, 12)
        ));
        return exp;
    }

    private Expedition createChoquequirao() {
        Expedition exp = Expedition.builder()
                .name("Choquequirao Trek")
                .slug("choquequirao-trek")
                .description("La 'hermana sagrada' de Machu Picchu. Trek de cuatro días hacia una ciudad inca menos visitada.")
                .price(520.00)
                .durationDays(4)
                .difficulty(Difficulty.HARD)
                .location("Cusco, Perú")
                .build();

        exp.getItineraries().addAll(List.of(
                itinerary(exp, 1, "Cachora - Chiquisca", "Transfer a Cachora. Descenso al río Apurímac."),
                itinerary(exp, 2, "Choquequirao", "Ascenso empinado hasta las ruinas de Choquequirao."),
                itinerary(exp, 3, "Exploración", "Día completo explorando terrazas y plazas."),
                itinerary(exp, 4, "Retorno", "Regreso a Cachora. Transfer a Cusco.")
        ));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1530999811698-221aa9eb1739?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1590835192370-3941b97e93fd?w=1080", 2)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 18), LocalDate.of(2026, 6, 22), 14, 9),
                availability(exp, LocalDate.of(2026, 7, 12), LocalDate.of(2026, 7, 16), 14, 14)
        ));
        return exp;
    }

    private Expedition createPastoruri() {
        Expedition exp = Expedition.builder()
                .name("Glaciar Pastoruri")
                .slug("glaciar-pastoruri")
                .description("Excursión de un día al glaciar Pastoruri en la Cordillera Blanca.")
                .price(55.00)
                .durationDays(1)
                .difficulty(Difficulty.EASY)
                .location("Huaraz, Perú")
                .build();

        exp.getItineraries().add(itinerary(exp, 1, "Glaciar Pastoruri", "Salida desde Huaraz. Paradas en bosque de Puyas Raimondi."));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1708394534994-4e66c2b09e1f?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1736390739681-ab6580f53adc?w=1080", 2)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 7), LocalDate.of(2026, 6, 7), 20, 20),
                availability(exp, LocalDate.of(2026, 6, 14), LocalDate.of(2026, 6, 14), 20, 15),
                availability(exp, LocalDate.of(2026, 7, 8), LocalDate.of(2026, 7, 8), 20, 6)
        ));
        return exp;
    }

    private Expedition createLares() {
        Expedition exp = Expedition.builder()
                .name("Lares Trek")
                .slug("lares-trek")
                .description("Trek cultural de tres días por comunidades andinas tradicionales.")
                .price(380.00)
                .durationDays(3)
                .difficulty(Difficulty.MODERATE)
                .location("Cusco, Perú")
                .build();

        exp.getItineraries().addAll(List.of(
                itinerary(exp, 1, "Lares - Huacahuasi", "Transfer a Lares. Baño en aguas termales."),
                itinerary(exp, 2, "Paso Ipsaycocha", "Cruce del paso Ipsaycocha (4,500 msnm)."),
                itinerary(exp, 3, "Ollantaytambo - Machu Picchu", "Descenso a Ollantaytambo. Tren a Aguas Calientes.")
        ));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1637580981046-6e14709be202?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1598859409659-b88fc15bbc2f?w=1080", 2)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 9), LocalDate.of(2026, 6, 12), 16, 11),
                availability(exp, LocalDate.of(2026, 7, 18), LocalDate.of(2026, 7, 21), 16, 16)
        ));
        return exp;
    }

    private Expedition createSantaCruz() {
        Expedition exp = Expedition.builder()
                .name("Santa Cruz Trek")
                .slug("santa-cruz-trek")
                .description("El trek más popular de la Cordillera Blanca. Cuatro días atravesando valles glaciares.")
                .price(420.00)
                .durationDays(4)
                .difficulty(Difficulty.MODERATE)
                .location("Huaraz, Perú")
                .build();

        exp.getItineraries().addAll(List.of(
                itinerary(exp, 1, "Cashapampa - Llamacorral", "Inicio del trek desde Cashapampa."),
                itinerary(exp, 2, "Laguna Ichiccocha", "Caminata hasta la laguna Ichiccocha."),
                itinerary(exp, 3, "Paso Punta Unión", "Ascenso al paso Punta Unión (4,750 msnm)."),
                itinerary(exp, 4, "Vaquería", "Descenso final a Vaquería.")
        ));
        exp.getImages().addAll(List.of(
                image(exp, "https://images.unsplash.com/photo-1551779382-e65b3ec856ef?w=1080", 1),
                image(exp, "https://images.unsplash.com/photo-1575928185982-7660337c15e2?w=1080", 2)
        ));
        exp.getAvailabilities().addAll(List.of(
                availability(exp, LocalDate.of(2026, 6, 16), LocalDate.of(2026, 6, 20), 18, 13),
                availability(exp, LocalDate.of(2026, 7, 25), LocalDate.of(2026, 7, 29), 18, 18),
                availability(exp, LocalDate.of(2026, 8, 15), LocalDate.of(2026, 8, 19), 18, 3)
        ));
        return exp;
    }

    private Itinerary itinerary(Expedition exp, int day, String title, String desc) {
        Itinerary it = new Itinerary();
        it.setExpedition(exp);
        it.setDayNumber(day);
        it.setTitle(title);
        it.setDescription(desc);
        return it;
    }

    private Image image(Expedition exp, String url, int order) {
        Image img = new Image();
        img.setExpedition(exp);
        img.setUrl(url);
        img.setImageOrder(order);
        return img;
    }

    private Availability availability(Expedition exp, LocalDate start, LocalDate end, int cap, int spots) {
        Availability av = new Availability();
        av.setExpedition(exp);
        av.setStartDate(start);
        av.setEndDate(end);
        av.setCapacity(cap);
        av.setAvailableSpots(spots);
        return av;
    }
}
