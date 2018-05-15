import { BaseEntity } from '../../shared';
import { BizDev } from '../biz-dev/biz-dev.model';
import { Consultant } from '../consultant/consultant.model';

export const enum Statut {
    'GO',
    'NOGO',
    'STANDBY',
    'POSITIONEMENT',
    'ENTRETIEN_COMMERCIAL',
    'ENTRETIEN_CLIENT',
    'CV_ENVOYE'
}

export class Placement implements BaseEntity {
    constructor(
        public id?: number,
        public nomClientFinal?: string,
        public nomSSII?: string,
        public contactSSII?: string,
        public contactClient?: string,
        public dateDemarrage?: any,
        public etat?: Statut,
        public archived?: boolean,
        public tjmNego?: number,
        public consultant?: Consultant,
        public bizDev?: BizDev,
    ) {
    }
}
