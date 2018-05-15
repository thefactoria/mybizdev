import { BaseEntity } from '../../shared';
import { BizDev } from '../biz-dev/biz-dev.model';
import { Consultant } from '../consultant/consultant.model';

export const enum Statut {
    'GO',
    'NOGO',
    'STANDBY',
    'INPROGRESS'
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
        public consultant?: Consultant,
        public bizDev?: BizDev,
    ) {
    }
}
