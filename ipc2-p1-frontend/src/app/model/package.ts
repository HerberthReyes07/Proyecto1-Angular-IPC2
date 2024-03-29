import { PackageStatus } from "./package-status";

export class Package {
    id!: number;
    customerId!: number;
    destinationId!: number;
    weight!: number;
    shippingCost!: number;
    status!: PackageStatus;
    invoiceNo!: number;
}
