import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public class Buzzer extends ListenerAdapter {
    public AudioSendHandler audioSendHandler;
    public TrackScheduler trackScheduler;
    public OffsetDateTime lastBuzzer = null;

    public static void main(String[] args) {
        try{
            var builder = JDABuilder.createDefault("XXXX")
                    .addEventListeners(new Buzzer())
                    .setActivity(Activity.playing("/buzzer"))
                    .build();

            builder.upsertCommand("buzzer", "Make a sound of a buzzer").queue(); // This can take up to 1 hour to show up in the client
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        List<Member> listVoc = event.getChannelLeft().getMembers();
        if(listVoc.contains(event.getGuild().getSelfMember()) && listVoc.size() == 1){
            System.out.println("ouh");
            listVoc.get(0).getGuild().getAudioManager().closeAudioConnection();


        }
    }

    public AudioSendHandler getSendHandler() {
        return audioSendHandler;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        try{

            TextChannel salonText = event.getChannel();
            Message commandeMessage = event.getMessage();
            Member auteurCommande = event.getMessage().getMember();

            // On ignore les bots
            if(event.getAuthor().isBot()) {
                return;
            }

            if (event.getMessage().getContentRaw().equals("/nrv")) {
                // Condition pour savoir si on a les droit de rejoindre les salons vocaux
                if (!event.getGuild().getSelfMember().hasPermission(salonText, Permission.VOICE_CONNECT)) {
                    salonText.sendMessage("❌ | Je n'ai pas les droits pour rejoindre les salons vocaux ").queue();
                    return;
                }


                // Salon vocal dans lequel l'utilisateur qu'a envoyé la commande est présent
                VoiceChannel salonVoc = event.getMember().getVoiceState().getChannel();

                // Condition dans le cas l'utilisateur n'est pas présent dans un salon vocal
                if (salonVoc == null) {
                    commandeMessage.reply("❌ | Vous n'êtes dans aucun salon vocal").queue();
                    return;
                }

                if (event.getAuthor().equals(event.getJDA().getSelfUser())) return; // ignore own messages

                if (lastBuzzer != null && event.getMessage().getTimeCreated().isBefore(lastBuzzer)) {
                    return;
                } else {
                    lastBuzzer = event.getMessage().getTimeCreated().plusSeconds(3);
                    salonText.sendTyping().queue();

                    //On commence le processus de connexion au salon vocal
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    // When somebody really needs to chill.
                    audioManager.openAudioConnection(salonVoc);

                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    AudioSourceManagers.registerRemoteSources(playerManager);

                    AudioPlayer player = playerManager.createPlayer();

                    trackScheduler = new TrackScheduler(player);
                    player.addListener(trackScheduler);

                    audioSendHandler = new AudioPlayerSendHandler(player);

                    PlayerManager.getINSTANCE().loadAndPlay(salonText, "/root/5euros/Buzzer/Nrv.mp3");

                    salonText.sendMessage(event.getAuthor().getName() + " est nrv").queue();
                }
            }


            if (event.getMessage().getContentRaw().equals("/dog")) {
                // Condition pour savoir si on a les droit de rejoindre les salons vocaux
                if (!event.getGuild().getSelfMember().hasPermission(salonText, Permission.VOICE_CONNECT)) {
                    salonText.sendMessage("❌ | Je n'ai pas les droits pour rejoindre les salons vocaux ").queue();
                    return;
                }


                // Salon vocal dans lequel l'utilisateur qu'a envoyé la commande est présent
                VoiceChannel salonVoc = event.getMember().getVoiceState().getChannel();

                // Condition dans le cas l'utilisateur n'est pas présent dans un salon vocal
                if (salonVoc == null) {
                    commandeMessage.reply("❌ | Vous n'êtes dans aucun salon vocal").queue();
                    return;
                }

                if (event.getAuthor().equals(event.getJDA().getSelfUser())) return; // ignore own messages

                if (lastBuzzer != null && event.getMessage().getTimeCreated().isBefore(lastBuzzer)) {
                    return;
                } else {
                    lastBuzzer = event.getMessage().getTimeCreated().plusSeconds(3);
                    salonText.sendTyping().queue();

                    //On commence le processus de connexion au salon vocal
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    // When somebody really needs to chill.
                    audioManager.openAudioConnection(salonVoc);

                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    AudioSourceManagers.registerRemoteSources(playerManager);

                    AudioPlayer player = playerManager.createPlayer();

                    trackScheduler = new TrackScheduler(player);
                    player.addListener(trackScheduler);

                    audioSendHandler = new AudioPlayerSendHandler(player);

                    PlayerManager.getINSTANCE().loadAndPlay(salonText, "/root/5euros/Buzzer/Dog.mp3");

                    salonText.sendMessage(event.getAuthor().getName() + " aboie").queue();
                }
            }

            if (event.getMessage().getContentRaw().equals("/prout")) {
                // Condition pour savoir si on a les droit de rejoindre les salons vocaux
                if (!event.getGuild().getSelfMember().hasPermission(salonText, Permission.VOICE_CONNECT)) {
                    salonText.sendMessage("❌ | Je n'ai pas les droits pour rejoindre les salons vocaux ").queue();
                    return;
                }


                // Salon vocal dans lequel l'utilisateur qu'a envoyé la commande est présent
                VoiceChannel salonVoc = event.getMember().getVoiceState().getChannel();

                // Condition dans le cas l'utilisateur n'est pas présent dans un salon vocal
                if (salonVoc == null) {
                    commandeMessage.reply("❌ | Vous n'êtes dans aucun salon vocal").queue();
                    return;
                }

                if (event.getAuthor().equals(event.getJDA().getSelfUser())) return; // ignore own messages

                if (lastBuzzer != null && event.getMessage().getTimeCreated().isBefore(lastBuzzer)) {
                    return;
                } else {
                    lastBuzzer = event.getMessage().getTimeCreated().plusSeconds(3);
                    salonText.sendTyping().queue();

                    //On commence le processus de connexion au salon vocal
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    // When somebody really needs to chill.
                    audioManager.openAudioConnection(salonVoc);

                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    AudioSourceManagers.registerRemoteSources(playerManager);

                    AudioPlayer player = playerManager.createPlayer();

                    trackScheduler = new TrackScheduler(player);
                    player.addListener(trackScheduler);

                    audioSendHandler = new AudioPlayerSendHandler(player);

                    PlayerManager.getINSTANCE().loadAndPlay(salonText, "/root/5euros/Buzzer/Prout.mov");

                    salonText.sendMessage("Ouuuuuh ça puuue ici").queue();
                }
            }

            if (event.getMessage().getContentRaw().equals("/circus")) {
                // Condition pour savoir si on a les droit de rejoindre les salons vocaux
                if (!event.getGuild().getSelfMember().hasPermission(salonText, Permission.VOICE_CONNECT)) {
                    salonText.sendMessage("❌ | Je n'ai pas les droits pour rejoindre les salons vocaux ").queue();
                    return;
                }


                // Salon vocal dans lequel l'utilisateur qu'a envoyé la commande est présent
                VoiceChannel salonVoc = event.getMember().getVoiceState().getChannel();

                // Condition dans le cas l'utilisateur n'est pas présent dans un salon vocal
                if (salonVoc == null) {
                    commandeMessage.reply("❌ | Vous n'êtes dans aucun salon vocal").queue();
                    return;
                }

                if (event.getAuthor().equals(event.getJDA().getSelfUser())) return; // ignore own messages

                if (lastBuzzer != null && event.getMessage().getTimeCreated().isBefore(lastBuzzer)) {
                    return;
                } else {
                    lastBuzzer = event.getMessage().getTimeCreated().plusSeconds(3);
                    salonText.sendTyping().queue();

                    //On commence le processus de connexion au salon vocal
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    // When somebody really needs to chill.
                    audioManager.openAudioConnection(salonVoc);

                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    AudioSourceManagers.registerRemoteSources(playerManager);

                    AudioPlayer player = playerManager.createPlayer();

                    trackScheduler = new TrackScheduler(player);
                    player.addListener(trackScheduler);

                    audioSendHandler = new AudioPlayerSendHandler(player);

                    PlayerManager.getINSTANCE().loadAndPlay(salonText, "/root/5euros/Buzzer/Circus.mp3");

                    salonText.sendMessage(event.getAuthor().getName() + " enclenche le cirque").queue();
                }
            }


            if (event.getMessage().getContentRaw().equals("/buzzer")) {

                // Condition pour savoir si on a les droit de rejoindre les salons vocaux
                if(!event.getGuild().getSelfMember().hasPermission(salonText, Permission.VOICE_CONNECT)){
                    salonText.sendMessage("❌ | Je n'ai pas les droits pour rejoindre les salons vocaux ").queue();
                    return;
                }


                // Salon vocal dans lequel l'utilisateur qu'a envoyé la commande est présent
                VoiceChannel salonVoc = event.getMember().getVoiceState().getChannel();

                // Condition dans le cas l'utilisateur n'est pas présent dans un salon vocal
                if(salonVoc == null){
                    commandeMessage.reply("❌ | Vous n'êtes dans aucun salon vocal").queue();
                    return;
                }

                if (event.getAuthor().equals(event.getJDA().getSelfUser())) return; // ignore own messages

                if(lastBuzzer != null && event.getMessage().getTimeCreated().isBefore(lastBuzzer)){
                    return;
                }else{
                    lastBuzzer = event.getMessage().getTimeCreated().plusSeconds(3);
                    salonText.sendTyping().queue();

                    //On commence le processus de connexion au salon vocal
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    // When somebody really needs to chill.
                    audioManager.openAudioConnection(salonVoc);

                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    AudioSourceManagers.registerRemoteSources(playerManager);

                    AudioPlayer player = playerManager.createPlayer();

                    trackScheduler = new TrackScheduler(player);
                    player.addListener(trackScheduler);

                    audioSendHandler = new AudioPlayerSendHandler(player);

                    PlayerManager.getINSTANCE().loadAndPlay(salonText, "/root/5euros/Buzzer/Buzzer.mp3");

                    salonText.sendMessage(event.getAuthor().getName() + " a buzzé").queue();


                }





            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

