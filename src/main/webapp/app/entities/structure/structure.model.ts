import { BaseEntity } from './../../shared';

export class Structure implements BaseEntity {
    constructor(
        public id?: number,
        public denomination?: string,
        public telephone?: string,
        public adresse?: string,
        public ninea?: string,
        public capital?: number,
        public numeroIpres?: string,
        public numeroCss?: string,
        public logoContentType?: string,
        public logo?: any,
        public ipm?: boolean,
        public signatureContentType?: string,
        public signature?: any,
        public montantIpm?: number,
        public deleted?: boolean,
        public convention?: BaseEntity,
    ) {
        this.ipm = false;
        this.deleted = false;
    }
}
