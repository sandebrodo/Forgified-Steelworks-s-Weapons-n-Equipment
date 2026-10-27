package steelworks.core.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class MoonAltarParticle extends TextureSheetParticle {
    protected MoonAltarParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z, xd, yd, zd);

        this.lifetime = 30;
        this.gravity = 0.0F;
        this.quadSize = 0.2F;
        this.hasPhysics = false;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;


        this.pickSprite(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = 1.0F - (float) this.age / this.lifetime;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

        @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
                return new MoonAltarParticle(level, x, y, z, xd, yd, zd, sprites);
            }
        }
}
