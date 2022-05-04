package com.infinium.global.entity.list;

import com.infinium.Infinium;
import com.infinium.api.utils.ChatFormatter;
import com.infinium.api.world.Location;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SuperNova extends WitherEntity {



    public SuperNova(EntityType<? extends SuperNova> entityType, World world) {
        super(entityType, world);
    }
    private static class SuperNovaGoal extends Goal {

        private final SuperNova superNova;
        private LivingEntity target;
        private int attackCooldown = 200;
        private int randomMessageCooldown = 420;
        private ATTACKS currentAttack = ATTACKS.NONE;
        private ThreadLocalRandom random;

        public SuperNovaGoal(SuperNova superNova){
            this.superNova = superNova;
            this.random = ThreadLocalRandom.current();
        }

        private static String novaPrefix() {

            return ChatFormatter.format("&8&lSuper Nova: &7");
        }



        @Override
        public boolean canStart() {
            return true;
        }


        @Override
        public void tick() {
            attackCooldown--;
            randomMessageCooldown--;
            if(randomMessageCooldown == 0){
                int r = new Random().nextInt(4);

                if(r == 0){
                    ChatFormatter.broadcastMessage(novaPrefix()+"&7Que buen dia para morir, ¿no?");

                } else if (r == 1) {
                    ChatFormatter.broadcastMessage(novaPrefix()+"&7¡Estan acabados!");

                } else if (r == 2) {
                    ChatFormatter.broadcastMessage(novaPrefix()+"&7Su final esta cerca...");

                } else {
                    ChatFormatter.broadcastMessage(novaPrefix()+"&7Jamas podreis derrotarme!");
                }
            }
            if(randomMessageCooldown == -1){
                randomMessageCooldown = 375 + random.nextInt(75) + 1;
            }

            if(attackCooldown == 0){
                random = ThreadLocalRandom.current();
                int r = random.nextInt(3) + 1;

                switch (r){
                    case 1 -> this.currentAttack = ATTACKS.MINI_MODE;
                    case 2 -> this.currentAttack = ATTACKS.WITHER_SKULLS_OP;
                    case 3 -> this.currentAttack = ATTACKS.ROTATE_WITHER_AND_EXPLOSION;
                    default -> this.currentAttack = ATTACKS.NEGATIVE_POTION_EFFECT;
                }
                doAttack();

            }

            if(attackCooldown == -1){
                random = ThreadLocalRandom.current();
                attackCooldown = 50 + random.nextInt(100);
                this.currentAttack = ATTACKS.NONE;
            }
        }
        private void doAttack(){
            switch (this.currentAttack){
                case MINI_MODE -> {
                    this.superNova.setInvulTimer(300);
                    this.superNova.setGlowing(true);
                    Infinium.executorService.schedule(() -> {
                        this.superNova.setInvulTimer(0);
                        this.superNova.setGlowing(false);
                    }, 15L, TimeUnit.SECONDS);
                }
                case NEGATIVE_POTION_EFFECT -> {

                    StatusEffectInstance[] potions = {
                            new StatusEffectInstance(StatusEffects.WITHER, 10 * 20, 14),
                            new StatusEffectInstance(StatusEffects.SLOWNESS, 10 * 20, 1),
                            new StatusEffectInstance(StatusEffects.NAUSEA, 10 * 20, 3),
                            new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 15 * 20, 4)};
                    for (int i = 0; i < 7; i++) {
                        Random r = new Random();
                        int X = (r.nextInt(10) + 5) * (r.nextBoolean() ? 1 : -1) ;
                        int Z = (r.nextInt(10) + 5) * (r.nextBoolean() ? 1 : -1);
                        int Y = superNova.getBlockY();
                        Location loc = new Location(superNova.getWorld(), X + superNova.getBlockX() , Y, Z + superNova.getBlockZ());

                        /*
                                        if(loc.getWorld().isClient()) {
                            ClientWorld world = (ClientWorld) loc.getWorld();
                            AreaEffectCloudEntity cloud = ((ClientWorld) loc.getWorld()).addEntity(new AreaEffectCloudEntity(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
                            cloud.setParticleType(ParticleTypes.LAVA);
                            cloud.addEffect(potions[random.nextInt(potions.length)]);
                            cloud.setRadius(5.0F);
                        }
                         */
                        //AreaEffectCloudEntity cloud = loc.getWorld().spawnEntity(new AreaEffectCloudEntity(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
                        random = ThreadLocalRandom.current();

                    }
                    random = ThreadLocalRandom.current();
                }
                case WITHER_SKULLS_OP -> {
                  /*
                    for (int i = 0; i < 12; i++) {
                        WitherSkullEntity skull = bWither.getWorld().spawn(bWither.getLocation(), WitherSkull.class);
                        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                        skull.setDirection(players.get(random.nextInt(players.size())).getLocation().toVector());
                        skull.setYield(3.5F);
                        random = ThreadLocalRandom.current();
                    }
                   */
                }
                case ROTATE_WITHER_AND_EXPLOSION -> {}
            }
        }
        private enum ATTACKS {
            NONE,
            MINI_MODE,
            WITHER_SKULLS_OP,
            ROTATE_WITHER_AND_EXPLOSION,
            NEGATIVE_POTION_EFFECT,
        }
    }


}
